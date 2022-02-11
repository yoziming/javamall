package com.yoziming.javamall.order.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付訊息表
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
@Data
@TableName("t_order_payment_info")
public class PaymentInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 訂單號（對外業務號）
     */
    private String orderSn;
    /**
     * 訂單id
     */
    private Long orderId;
    /**
     * 支付寶交易流水號
     */
    private String alipayTradeNo;
    /**
     * 支付總金額
     */
    private BigDecimal totalAmount;
    /**
     * 交易內容
     */
    private String subject;
    /**
     * 支付狀態
     */
    private String paymentStatus;
    /**
     * 創建時間
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 確認時間
     */
    private Date confirmTime;
    /**
     * 回調內容
     */
    private String callbackContent;
    /**
     * 回調時間
     */
    private Date callbackTime;

}
