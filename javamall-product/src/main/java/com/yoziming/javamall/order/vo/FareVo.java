package com.yoziming.javamall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:18
 * @Description:
 */
@Data
public class FareVo {
    private MemberAddressVo address;
    private BigDecimal fare;
}
