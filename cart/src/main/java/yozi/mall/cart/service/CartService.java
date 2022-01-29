package yozi.mall.cart.service;

import yozi.mall.cart.vo.CartItemVo;
import yozi.mall.cart.vo.CartVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 購物車服務
 */
public interface CartService {

    /**
     * 將商品添加至購物車
     */
    CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    /**
     * 獲取購物車某個購物項
     */
    CartItemVo getCartItem(Long skuId);

    /**
     * 獲取購物車裡面的信息
     *
     * @return
     */
    CartVo getCart() throws ExecutionException, InterruptedException;

    /**
     * 清空購物車的數據
     *
     * @param cartKey
     */
    public void clearCartInfo(String cartKey);

    /**
     * 勾選購物項
     *
     * @param skuId
     * @param check
     */
    void checkItem(Long skuId, Integer check);

    /**
     * 改變商品數量
     *
     * @param skuId
     * @param num
     */
    void changeItemCount(Long skuId, Integer num);

    /**
     * 刪除購物項
     *
     * @param skuId
     */
    void deleteIdCartInfo(Integer skuId);

    /**
     * 獲取當前用戶的購物車所有商品項
     *
     * @return
     */
    List<CartItemVo> getUserCartItems();

}
