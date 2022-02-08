package yoziming.mall.order.vo;

import lombok.Data;
import yoziming.mall.order.entity.OrderEntity;
import yoziming.mall.order.entity.OrderItemEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提交訂單接口：創建的訂單To對象
 * 1、訂單
 * 2、訂單項
 */
@Data
public class OrderCreateTo {
    private OrderEntity order;  // 訂單
    private List<OrderItemEntity> orderItems; // 訂單項
    /**
     * 訂單計算的應付價格
     **/
    private BigDecimal payPrice;
    /**
     * 運費
     **/
    private BigDecimal fare;
}
