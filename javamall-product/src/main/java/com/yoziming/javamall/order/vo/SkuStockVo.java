package com.yoziming.javamall.order.vo;

import lombok.Data;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:18
 * @Description:
 */
@Data
public class SkuStockVo {
    private Long skuId;
    private Boolean hasStock;
}
