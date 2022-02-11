package com.yoziming.javamall.order.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:18
 * @Description:
 */
@Data
public class WareSkuLockVo {
    private String orderSn; //訂單號
    private List<OrderItemVo> locks; //需要鎖住的所有庫存訊息
}
