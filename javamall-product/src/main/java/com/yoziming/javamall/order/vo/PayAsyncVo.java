package com.yoziming.javamall.order.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:18
 * @Description:
 */
@ToString
@Data
public class PayAsyncVo {

    private String gmt_create;
    private String charset;
    private String gmt_payment;
    private Date notify_time;
    private String subject;
    private String sign;
    private String buyer_id;//支付者的id
    private String body;//訂單的訊息
    private String invoice_amount;//支付金額
    private String version;
    private String notify_id;//通知id
    private String fund_bill_list;
    private String notify_type;//通知類型； trade_status_sync
    private String out_trade_no;//訂單號
    private String total_amount;//支付的總額
    private String trade_status;//交易狀態  TRADE_SUCCESS
    private String trade_no;//流水號
    private String auth_app_id;//
    private String receipt_amount;//商家收到的款
    private String point_amount;//
    private String app_id;//應用id
    private String buyer_pay_amount;//最終支付的金額
    private String sign_type;//簽名類型
    private String seller_id;//商家的id

}
