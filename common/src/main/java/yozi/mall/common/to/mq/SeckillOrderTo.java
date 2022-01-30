package yozi.mall.common.to.mq;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeckillOrderTo {

    /**
     * 訂單號
     */
    private String orderSn;

    /**
     * 活動場次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒殺價格
     */
    private BigDecimal seckillPrice;

    /**
     * 購買數量
     */
    private Integer num;

    /**
     * 會員ID
     */
    private Long memberId;

}
