package com.yoziming.javamall.cart.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 購物車
 *
 * @TableName t_cart
 */
@TableName(value = "t_cart")
@Data
public class CartEntity implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 商品sku編號
     */
    private Long skuId;
    /**
     * 會員id
     */
    private Long memberId;

    /**
     * 商品sku名字
     */
    private String skuName;

    /**
     * 商品sku標題
     */
    private String skuTitle;

    /**
     * 商品sku圖片
     */
    private String skuPic;

    /**
     * 商品sku價格
     */
    private BigDecimal skuPrice;

    /**
     * 商品銷售屬性組合（JSON）
     */
    private String skuAttrsVals;

    /**
     * 數量
     */
    private Integer count;

    /**
     * 是否被選中
     */
    private Boolean checked;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}