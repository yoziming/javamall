package yozi.mall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import yozi.mall.cart.exception.CartExceptionHandler;
import yozi.mall.cart.interceptor.CartInterceptor;
import yozi.mall.cart.service.CartService;
import yozi.mall.cart.vo.CartItemVo;
import yozi.mall.cart.vo.CartVo;
import yozi.mall.cart.vo.SkuInfoVo;
import yozi.mall.common.constant.CartConstant;
import yozi.mall.common.feign.ProductFeignService;
import yozi.mall.common.to.UserInfoTo;
import yozi.mall.common.utils.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
@Service("cartService")
public class CartServiceImpl implements CartService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private ThreadPoolExecutor executor;

    /**
     * 跳轉cartList頁面
     * 封裝購物車類【所有商品，所有商品的價格】
     * 【整合登入狀態與未登入狀態】
     */
    @Override
    public CartVo getCart() throws ExecutionException, InterruptedException {
        CartVo cartVo = new CartVo();
        UserInfoTo userInfoTo = CartInterceptor.toThreadLocal.get();
        System.out.println(userInfoTo);
        if (userInfoTo.getUserId() != null) {
            //  1）、登入後購物車的key
            String cartKey = CartConstant.CART_PREFIX + userInfoTo.getUserId();
            //  2）、臨時購物車的key
            String temptCartKey = CartConstant.CART_PREFIX + userInfoTo.getUserKey();
            // 如果臨時購物車的數據還未進行合併
            List<CartItemVo> tempCartItems = getCartItems(temptCartKey);
            if (tempCartItems != null) {
                // 臨時購物車有數據需要進行合併操作
                for (CartItemVo item : tempCartItems) {
                    addToCart(item.getSkuId(), item.getCount());
                }
                // 清除臨時購物車的數據
                clearCartInfo(temptCartKey);
            }
            // 獲取登入後的購物車數據【包含合併過來的臨時購物車的數據和登入後購物車的數據】
            List<CartItemVo> cartItems = getCartItems(cartKey);
            cartVo.setItems(cartItems);
        } else {
            // 沒登入
            String cartKey = CartConstant.CART_PREFIX + userInfoTo.getUserKey();
            // 獲取臨時購物車裡面的所有購物項
            List<CartItemVo> cartItems = getCartItems(cartKey);
            cartVo.setItems(cartItems);
        }
        return cartVo;
    }

    /**
     * 添加商品到購物車
     *
     * @param skuId
     * @param num
     */
    @Override
    public CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        // 拿到要操作的購物車信息【cartOps就相當於綁定了當前用戶購物車數據的hash】
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        // 判斷Redis是否有該商品的信息
        String productRedisValue = (String) cartOps.get(skuId.toString());
        // 如果沒有就添加數據【遠程查詢skuId】
        if (StringUtils.isEmpty(productRedisValue)) {
            // 添加新的商品到購物車(redis)
            CartItemVo cartItem = new CartItemVo();
            // 開啟第一個異步任務
            CompletableFuture<Void> getSkuInfoFuture = CompletableFuture.runAsync(() -> {
                // 遠程查詢當前要添加商品的信息
                R productSkuInfo = productFeignService.getInfo(skuId);
                SkuInfoVo skuInfo = productSkuInfo.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                });
                // 數據賦值操作
                cartItem.setSkuId(skuInfo.getSkuId());
                cartItem.setTitle(skuInfo.getSkuTitle());
                cartItem.setImage(skuInfo.getSkuDefaultImg());
                cartItem.setPrice(skuInfo.getPrice());
                cartItem.setCount(num);
                cartItem.setCheck(true);
            }, executor);

            // 開啟第二個異步任務
            CompletableFuture<Void> getSkuAttrValuesFuture = CompletableFuture.runAsync(() -> {
                // 遠程查詢skuAttrValues組合信息
                List<String> skuSaleAttrValues = productFeignService.getSkuSaleAttrValues(skuId);
                cartItem.setSkuAttrValues(skuSaleAttrValues);
            }, executor);
            // 等待所有的異步任務全部完成
            CompletableFuture.allOf(getSkuInfoFuture, getSkuAttrValuesFuture).get();
            // 序列化並存到redis
            String cartItemJson = JSON.toJSONString(cartItem);
            cartOps.put(skuId.toString(), cartItemJson);
            return cartItem;
        } else {
            // 購物車中已有此商品，修改數量即可
            CartItemVo cartItemVo = JSON.parseObject(productRedisValue, CartItemVo.class);
            cartItemVo.setCount(cartItemVo.getCount() + num);
            // 修改redis的數據
            String cartItemJson = JSON.toJSONString(cartItemVo);
            cartOps.put(skuId.toString(), cartItemJson);
            return cartItemVo;
        }
    }

    /**
     * 獲取到我們要操作的購物車
     * 簡化代碼：
     * 1、判斷是否登入，拼接key
     * 2、數據是hash類型，所以每次要調用兩次key【直接綁定外層key】
     * 第一層key：mall:cart:2
     * 第二層key：skuId
     */
    private BoundHashOperations<String, Object, Object> getCartOps() {
        // 先得到當前用戶信息
        UserInfoTo userInfoTo = CartInterceptor.toThreadLocal.get();
        String cartKey = "";
        if (userInfoTo.getUserId() != null) {
            // mall:cart:UserID
            cartKey = CartConstant.CART_PREFIX + userInfoTo.getUserId();
        } else {
            cartKey = CartConstant.CART_PREFIX + userInfoTo.getUserKey();
        }
        // 綁定指定的key操作Redis
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(cartKey);
        return operations;
    }

    /**
     * 重定向頁面獲取當前購物車中sku商品信息
     *
     * @param skuId
     * @return
     */
    @Override
    public CartItemVo getCartItem(Long skuId) {
        //拿到要操作的購物車信息
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String redisValue = (String) cartOps.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(redisValue, CartItemVo.class);
        return cartItemVo;
    }

    /**
     * 遠程調用：訂單服務調用【更新最新價格】
     * 獲取當前用戶購物車所有選中的商品項check=true【從redis中取】
     */
    @Override
    public List<CartItemVo> getUserCartItems() {
        List<CartItemVo> cartItemVoList = new ArrayList<>();
        // 獲取當前用戶登入的信息
        UserInfoTo userInfoTo = CartInterceptor.toThreadLocal.get();
        // 如果用戶未登入直接返回null
        if (userInfoTo.getUserId() == null) {
            return null;
        } else {
            // 獲取購物車項
            String cartKey = CartConstant.CART_PREFIX + userInfoTo.getUserId();
            // 獲取所有的
            List<CartItemVo> cartItems = getCartItems(cartKey);
            if (cartItems == null) {
                throw new CartExceptionHandler();
            }
            // 篩選出選中的
            cartItemVoList = cartItems.stream()
                    .filter(items -> items.getCheck())
                    .map(item -> {
                        // 更新為最新的價格（查詢數據庫）
                        // redis中的價格不是最新的
                        BigDecimal price = productFeignService.getPrice(item.getSkuId());
                        item.setPrice(price);
                        return item;
                    })
                    .collect(Collectors.toList());
        }
        return cartItemVoList;
    }

    /**
     * 獲取購物車裡面的數據【根據key，包裝成List<CartItemVo>】，合併臨時車用
     * key=【mall:cart:2 或 mall:cart:lkajkashjghj2989dsj】
     *
     * @param cartKey
     * @return
     */
    private List<CartItemVo> getCartItems(String cartKey) {
        // 獲取購物車裡面的所有商品
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(cartKey);
        List<Object> values = operations.values();
        if (values != null && values.size() > 0) {
            List<CartItemVo> cartItemVoStream = values.stream().map((obj) -> {
                String str = (String) obj;
                CartItemVo cartItem = JSON.parseObject(str, CartItemVo.class);
                return cartItem;
            }).collect(Collectors.toList());
            return cartItemVoStream;
        }
        return null;
    }

    @Override
    public void clearCartInfo(String cartKey) {
        redisTemplate.delete(cartKey);
    }

    @Override
    public void checkItem(Long skuId, Integer check) {

        //查詢購物車裡面的商品
        CartItemVo cartItem = getCartItem(skuId);
        //修改商品狀態
        cartItem.setCheck(check == 1 ? true : false);

        //序列化存入redis中
        String redisValue = JSON.toJSONString(cartItem);

        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.put(skuId.toString(), redisValue);

    }

    /**
     * 修改購物項數量
     *
     * @param skuId
     * @param num
     */
    @Override
    public void changeItemCount(Long skuId, Integer num) {
        //查詢購物車裡面的商品
        CartItemVo cartItem = getCartItem(skuId);
        cartItem.setCount(num);
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        //序列化存入redis中
        String redisValue = JSON.toJSONString(cartItem);
        cartOps.put(skuId.toString(), redisValue);
    }

    /**
     * 刪除購物項
     *
     * @param skuId
     */
    @Override
    public void deleteIdCartInfo(Integer skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.delete(skuId.toString());
    }
}
