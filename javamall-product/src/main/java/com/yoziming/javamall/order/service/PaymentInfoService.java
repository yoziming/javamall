package com.yoziming.javamall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.order.entity.PaymentInfoEntity;

import java.util.Map;

/**
 * 支付訊息表
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
public interface PaymentInfoService extends IService<PaymentInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

