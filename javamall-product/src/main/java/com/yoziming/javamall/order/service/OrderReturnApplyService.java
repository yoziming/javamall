package com.yoziming.javamall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.order.entity.OrderReturnApplyEntity;

import java.util.Map;

/**
 * 訂單退貨申請
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
public interface OrderReturnApplyService extends IService<OrderReturnApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void returnProduct(String orderSn, String reason, String returnAmount);

    void updateReturn(OrderReturnApplyEntity orderReturnApply);
}

