package com.yoziming.javamall.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.vo.MemberRespVo;
import com.yoziming.javamall.cart.dao.CartDao;
import com.yoziming.javamall.cart.entity.CartEntity;
import com.yoziming.javamall.cart.service.CartService;
import com.yoziming.javamall.cart.vo.Cart;
import com.yoziming.javamall.cart.vo.CartItem;
import com.yoziming.javamall.interceptor.LoginUserInterceptor;
import com.yoziming.javamall.product.controller.SkuInfoController;
import com.yoziming.javamall.product.controller.SkuSaleAttrValueController;
import com.yoziming.javamall.product.entity.SkuInfoEntity;
import com.yoziming.javamall.product.service.SkuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @Author: yoziming
 * @Date: 2022/2/19 19:21
 */
@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartDao, CartEntity> implements CartService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    SkuInfoController skuInfoController;

    @Autowired
    SkuSaleAttrValueController skuSaleAttrValueController;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    SkuInfoService skuInfoService;

    /**
     * 將商品添加到購物車
     *
     * @param skuId
     * @param num
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public void addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {

        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sku_id", skuId)
                .eq("member_id", memberRespVo.getId());
        CartEntity cartEntity = this.getOne(queryWrapper);
        if (Objects.isNull(cartEntity)) {
            // 購物車中沒有，要添加
            CartEntity cartEntity2 = new CartEntity();
            SkuInfoEntity skuInfoEntity = skuInfoService.getById(skuId);
            cartEntity2.setMemberId(memberRespVo.getId());
            cartEntity2.setSkuId(skuInfoEntity.getSkuId());
            cartEntity2.setSkuTitle(skuInfoEntity.getSkuTitle());
            cartEntity2.setSkuPic(skuInfoEntity.getSkuDefaultImg());
            cartEntity2.setCount(num);
            cartEntity2.setChecked(false);
            cartEntity2.setSkuPrice(skuInfoEntity.getPrice());
            List<String> values = skuSaleAttrValueController.getSkuSaleAttrValues(skuId);
            String skuAttr = StringUtils.collectionToDelimitedString(values, ";");
            cartEntity2.setSkuAttrsVals(skuAttr);
            this.save(cartEntity2);
        } else {
            // 有 加count
            CartEntity cartEntity2 = new CartEntity();
            cartEntity2.setId(cartEntity.getId());
            cartEntity2.setCount(cartEntity.getCount() + num);
            this.updateById(cartEntity2);
        }
    }

    /**
     * 獲取購物車中某個購物項
     *
     * @param skuId
     * @return
     */
    @Override
    public CartItem getCartItem(Long skuId) {

        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sku_id", skuId)
                .eq("member_id", memberRespVo.getId());
        CartEntity cartEntity = this.getOne(queryWrapper);

        CartItem cartItem = new CartItem();
        cartItem.setSkuId(cartEntity.getSkuId());
        cartItem.setTitle(cartEntity.getSkuTitle());
        cartItem.setImage(cartEntity.getSkuPic());
        cartItem.setCount(cartEntity.getCount());
        cartItem.setCheck(cartEntity.getChecked());
        cartItem.setPrice(cartEntity.getSkuPrice());
        cartItem.setTotalPrice(cartEntity.getSkuPrice().multiply(BigDecimal.valueOf(cartEntity.getCount())));
        List<String> values = skuSaleAttrValueController.getSkuSaleAttrValues(skuId);
        cartItem.setSkuAttr(values);

        return cartItem;
    }

    @Override
    public Cart getCart() throws ExecutionException, InterruptedException {
        Cart cart = new Cart();
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();

        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberRespVo.getId());
        List<CartEntity> cartEntityList = this.list(queryWrapper);

        List<CartItem> cartItemList = cartEntityList.stream().map(cartEntity -> {
            CartItem cartItem = new CartItem();
            cartItem.setSkuId(cartEntity.getSkuId());
            cartItem.setTitle(cartEntity.getSkuTitle());
            cartItem.setImage(cartEntity.getSkuPic());
            cartItem.setCount(cartEntity.getCount());
            cartItem.setCheck(cartEntity.getChecked());
            cartItem.setPrice(cartEntity.getSkuPrice());
            cartItem.setTotalPrice(cartEntity.getSkuPrice().multiply(BigDecimal.valueOf(cartEntity.getCount())));
            List<String> values = skuSaleAttrValueController.getSkuSaleAttrValues(cartEntity.getSkuId());
            cartItem.setSkuAttr(values);
            return cartItem;
        }).collect(Collectors.toList());
        cart.setItems(cartItemList);
        return cart;
    }

    /**
     * 勾選購物項
     *
     * @param skuId
     * @param check
     */
    @Override
    public void checkItem(Long skuId, Integer check) {

        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sku_id", skuId)
                .eq("member_id", memberRespVo.getId());
        CartEntity cartEntity = this.getOne(queryWrapper);
        cartEntity.setChecked(check == 1 ? true : false);
        this.updateById(cartEntity);
    }

    /**
     * 修改購物項數量
     *
     * @param skuId
     * @param num
     */
    @Override
    public void changeItemCount(Long skuId, Integer num) {

        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sku_id", skuId)
                .eq("member_id", memberRespVo.getId());
        CartEntity cartEntity = this.getOne(queryWrapper);
        cartEntity.setCount(num);
        this.updateById(cartEntity);
    }

    /**
     * 刪除購物項
     *
     * @param skuId
     */
    @Override
    public void deleteItem(Long skuId) {

        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sku_id", skuId)
                .eq("member_id", memberRespVo.getId());
        CartEntity cartEntity = this.getOne(queryWrapper);
        this.removeById(cartEntity);
    }

    @Override
    public List<CartItem> getUserCartItems(MemberRespVo memberRespVo) {

        if (memberRespVo.getId() == null) {
            return null;
        } else {

            QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("member_id", memberRespVo.getId());
            List<CartEntity> cartEntityList = this.list(queryWrapper);

            List<CartItem> cartItemList = cartEntityList.stream()
                    .filter(item -> item.getChecked())
                    .map(cartEntity -> {
                        CartItem cartItem = new CartItem();
                        cartItem.setSkuId(cartEntity.getSkuId());
                        cartItem.setTitle(cartEntity.getSkuTitle());
                        cartItem.setImage(cartEntity.getSkuPic());
                        cartItem.setCount(cartEntity.getCount());
                        cartItem.setCheck(cartEntity.getChecked());

                        SkuInfoEntity skuInfoEntity = skuInfoService.getById(cartEntity.getSkuId());

                        cartItem.setPrice(skuInfoEntity.getPrice());
                        cartItem.setTotalPrice(cartEntity.getSkuPrice().multiply(BigDecimal.valueOf(cartEntity.getCount())));
                        List<String> values = skuSaleAttrValueController.getSkuSaleAttrValues(cartEntity.getSkuId());
                        cartItem.setSkuAttr(values);
                        return cartItem;
                    }).collect(Collectors.toList());
            return cartItemList;

        }
    }

}
