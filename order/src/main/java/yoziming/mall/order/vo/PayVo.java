package yoziming.mall.order.vo;

import lombok.Data;

@Data
public class PayVo {

    private String out_trade_no; // 商戶訂單號 必填
    private String subject; // 訂單名稱 必填
    private String total_amount;  // 付款金額 必填
    private String body; // 商品描述 可空
}
