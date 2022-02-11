package com.yoziming.javamall.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.vo.MemberRespVo;
import com.yoziming.javamall.cart.entity.CartEntity;
import com.yoziming.javamall.cart.vo.Cart;
import com.yoziming.javamall.cart.vo.CartItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author: yoziming
 * @Date: 2022/2/19 19:20
 */
public interface CartService extends IService<CartEntity> {
    /**
     * 將商品添加到購物車
     *
     * @param skuId
     * @param num
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    void addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    /**
     * 獲取購物車中某個購物項
     *
     * @param skuId
     * @return
     */
    CartItem getCartItem(Long skuId);

    Cart getCart() throws ExecutionException, InterruptedException;

    /**
     * 勾選購物項
     *
     * @param skuId
     * @param check
     */
    void checkItem(Long skuId, Integer check);

    /**
     * 修改購物項數量
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
    void deleteItem(Long skuId);

    List<CartItem> getUserCartItems(MemberRespVo memberRespVo);
}
