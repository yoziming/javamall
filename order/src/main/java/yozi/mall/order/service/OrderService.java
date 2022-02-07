package yozi.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.to.mq.SeckillOrderTo;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.order.entity.OrderEntity;
import yozi.mall.order.vo.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 訂單
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 訂單確認頁返回需要用的數據
     *
     * @return
     */
    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    /**
     * 創建訂單
     *
     * @param vo
     * @return
     */
    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

    /**
     * 按照訂單號獲取訂單信息
     *
     * @param orderSn
     * @return
     */
    OrderEntity getOrderByOrderSn(String orderSn);

    /**
     * 關閉訂單
     *
     * @param orderEntity
     */
    void closeOrder(OrderEntity orderEntity);

    /**
     * 獲取當前訂單的支付信息
     *
     * @param orderSn
     * @return
     */
    PayVo getOrderPay(String orderSn);

    /**
     * 查詢當前用戶所有訂單數據
     *
     * @param params
     * @return
     */
    PageUtils queryPageWithItem(Map<String, Object> params);

    /**
     * 支付寶異步通知處理訂單數據
     *
     * @param asyncVo
     * @return
     */
    String handlePayResult(PayAsyncVo asyncVo);

    // /**
    //  * 微信異步通知處理
    //  * @param notifyData
    //  */
    // String asyncNotify(String notifyData);
    //
    //

    /**
     * 創建秒殺單
     *
     * @param orderTo
     */
    void createSeckillOrder(SeckillOrderTo orderTo);
}

