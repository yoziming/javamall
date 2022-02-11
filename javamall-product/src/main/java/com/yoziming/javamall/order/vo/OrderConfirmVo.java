package com.yoziming.javamall.order.vo;

import com.yoziming.javamall.cart.vo.CartItem;
import com.yoziming.javamall.member.entity.MemberReceiveAddressEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:17
 * @Description:
 */
@ToString
public class OrderConfirmVo {
    //收貨地址
    @Setter
    @Getter
    List<MemberReceiveAddressEntity> address;

    //所有選中的購物項
    @Setter
    @Getter
    List<CartItem> items;

    //發票記錄...

    //優惠卷訊息
    @Setter
    @Getter
    Integer integration;

    //防重令牌
    @Setter
    @Getter
    String orderToken;

    @Setter
    @Getter
    Map<Long, Boolean> stocks;
    //訂單總額
    //    BigDecimal total;

    public Integer getCount() {
        Integer i = 0;
        if (items != null) {
            for (CartItem item : items) {
                i += item.getCount();
            }
        }
        return i;
    }

    public BigDecimal getTotal() {
        BigDecimal sum = new BigDecimal("0");
        if (items != null) {
            for (CartItem item : items) {
                BigDecimal multiply = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));

                sum = sum.add(multiply);
            }
        }

        return sum;
    }

    //應付價格
    //    BigDecimal payPrice;

    public BigDecimal getPayPrice() {
        return getTotal();
    }

}
