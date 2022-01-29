package yozi.mall.order.vo;

import lombok.Data;
import yozi.mall.common.to.MemberAddressTo;

import java.math.BigDecimal;

@Data
public class FareVo {
    private MemberAddressTo address;
    private BigDecimal fare;
}
