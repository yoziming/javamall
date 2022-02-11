package com.yoziming.javamall.order.to;

import com.yoziming.javamall.order.entity.OrderEntity;
import com.yoziming.javamall.order.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/2/26 11:15
 * @Description:
 */
@Data
public class OrderCreateTo {
    private OrderEntity order;
    private List<OrderItemEntity> orderItems;
    private BigDecimal payPrice; //訂單計算的應付價格
    private BigDecimal fare; //運費
}

