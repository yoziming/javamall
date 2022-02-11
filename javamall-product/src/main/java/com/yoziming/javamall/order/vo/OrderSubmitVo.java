package com.yoziming.javamall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:18
 * @Description:
 */
@Data
public class OrderSubmitVo {
    private Long addrId; //收貨地址id
    private Integer payType; //支付方式
    //無需提交需要購買的商品，去購物車再獲取一遍
    //優惠，發票
    private String orderToken; //防重令牌
    private BigDecimal payPrice; //應付價格
    private String note; //訂單備註
    //用戶相關訊息，直接去session取出登入的用戶
}
