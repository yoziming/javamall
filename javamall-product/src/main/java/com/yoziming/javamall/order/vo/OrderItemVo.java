package com.yoziming.javamall.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:18
 * @Description:
 */
@Data
public class OrderItemVo {
    private Long skuId;
    private String title;
    private String image;
    private List<String> skuAttr;
    private BigDecimal price;
    private Integer count;
    private BigDecimal totalPrice;

    //查詢庫存狀態
    private boolean hasStock = true;
    private BigDecimal weight; //商品的重量
}
