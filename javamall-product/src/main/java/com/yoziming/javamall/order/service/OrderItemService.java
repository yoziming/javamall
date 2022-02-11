package com.yoziming.javamall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.order.entity.OrderItemEntity;

import java.util.Map;

/**
 * 訂單項訊息
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

