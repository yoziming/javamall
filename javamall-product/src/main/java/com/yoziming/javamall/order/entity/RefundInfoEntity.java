package com.yoziming.javamall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退款訊息
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
@Data
@TableName("t_order_refund_info")
public class RefundInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 退款的訂單
     */
    private String orderReturnId;
    /**
     * 退款金額
     */
    private BigDecimal refund;
    /**
     * 退款交易流水號
     */
    private String refundSn;
    /**
     * 退款狀態
     */
    private Integer refundStatus;
    /**
     * 退款渠道[1-支付寶，2-微信，3-銀聯，4-匯款]
     */
    private Integer refundChannel;
    /**
     *
     */
    private String refundContent;

}
