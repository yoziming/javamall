package com.yoziming.javamall.ware.vo;

import com.yoziming.javamall.member.entity.MemberReceiveAddressEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yoziming
 * @Date: 2022/1/19 15:10
 * @Description:
 */

@Data
public class FareVo {

    private MemberReceiveAddressEntity address;

    private BigDecimal fare;

}


