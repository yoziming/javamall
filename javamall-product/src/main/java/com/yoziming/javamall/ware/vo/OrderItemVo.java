package com.yoziming.javamall.ware.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/1/19 15:10
 * @Description:
 */

@Data
public class OrderItemVo {

    private Long skuId;

    private Boolean check;

    private String title;

    private String image;

    /**
     * 商品套餐屬性
     */
    private List<String> skuAttrValues;

    private BigDecimal price;

    private Integer count;

    private BigDecimal totalPrice;

    /**
     * 商品重量
     **/
    private BigDecimal weight = new BigDecimal("0.085");
}
