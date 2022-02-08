package yoziming.mall.order.vo;

import lombok.Getter;
import lombok.Setter;
import yoziming.mall.common.to.MemberAddressTo;
import yoziming.mall.common.to.OrderItemTo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 訂單確認頁需要用的數據
 */
public class OrderConfirmVo {

    /**
     * 會員收貨地址列表
     **/
    @Getter
    @Setter
    List<MemberAddressTo> memberAddressTos;

    /**
     * 所有選中的購物項【購物車中的所有項】
     **/
    @Getter
    @Setter
    List<OrderItemTo> items;

    /**
     * 優惠券（會員積分）
     **/
    @Getter
    @Setter
    private Integer integration;

    /**
     * 防止重複提交的令牌 冪等性
     **/
    @Getter
    @Setter
    private String orderToken;

    @Getter
    @Setter
    Map<Long, Boolean> stocks;

    public Integer getCount() {
        Integer count = 0;
        if (items != null && items.size() > 0) {
            for (OrderItemTo item : items) {
                count += item.getCount();
            }
        }
        return count;
    }

    /**
     * 總商品金額
     **/
    //BigDecimal total;
    //計算訂單總額
    public BigDecimal getTotal() {
        BigDecimal totalNum = BigDecimal.ZERO;
        if (items != null && items.size() > 0) {
            for (OrderItemTo item : items) {
                //計算當前商品的總價格
                BigDecimal itemPrice = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
                //再計算全部商品的總價格
                totalNum = totalNum.add(itemPrice);
            }
        }
        return totalNum;
    }

    /**
     * 應付總額
     **/
    //BigDecimal payPrice;
    public BigDecimal getPayPrice() {
        return getTotal();
    }
}
