package com.yoziming.javamall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/2/19 19:18
 */
public class Cart {
    List<CartItem> items;
    private Integer countNum; //商品數量
    private Integer countType; //商品類型數量
    private BigDecimal totalAmount; //商品總價
    private BigDecimal reduce = new BigDecimal("0"); //減免價格

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int count = 0;
        if (items != null && items.size() > 0) {
            for (CartItem item : items) {
                count += item.getCount();
            }
        }
        return count;
    }

    public Integer getCountType() {
        int count = 0;
        if (items != null && items.size() > 0) {
            for (CartItem item : items) {
                count += 1;
            }
        }
        return count;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0");
        //1、計算購物項總價
        if (items != null && items.size() > 0) {
            for (CartItem item : items) {
                if (item.getCheck()) {
                    BigDecimal totalPrice = item.getTotalPrice();
                    amount = amount.add(totalPrice);
                }
            }
        }
        //2、減去優惠總價
        BigDecimal subtract = amount.subtract(getReduce());

        return subtract;
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
