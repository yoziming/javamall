package com.yoziming.javamall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.order.entity.OrderEntity;
import com.yoziming.javamall.order.vo.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 訂單
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

    OrderEntity getOrderByOrderSn(String orderSn);

    PageUtils queryPageWithItem(Map<String, Object> params);

    PageUtils queryPageWithItem2(Integer pageNum);

    void closeOrder(OrderEntity entity);

    /**
     * 獲取當前訂單的支付訊息
     *
     * @param orderSn
     * @return
     */
    PayVo getOrderPay(String orderSn);

    String handlePayResult(PayAsyncVo vo);

    String handlePayResult(String orderSn);

    /**
     * 取消訂單
     */
    void cancelOrder(String orderSn);

    /**
     * 確認收貨
     *
     * @param orderSn
     */
    void confirmReceipt(String orderSn);

    /**
     * 獲取訂單詳情頁訊息
     *
     * @param orderSn
     */
    OrderEntity getOrderDetail(String orderSn);

    void updateOrder(OrderEntity order);

    void forcePaySuccess(String orderSn);
}

