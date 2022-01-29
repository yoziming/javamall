package yozi.mall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 封裝訂單提交數據的vo
 */
@Data
public class OrderSubmitVo {

    /**
     * 收貨地址的id
     **/
    private Long addrId;

    /**
     * 支付方式
     **/
    private Integer payType;
    //無需提交要購買的商品，去購物車再獲取一遍
    //優惠、發票

    /**
     * 防重令牌
     **/
    private String orderToken;

    /**
     * 應付價格
     **/
    private BigDecimal payPrice;

    /**
     * 訂單備註
     **/
    private String remarks;

    //用戶相關的信息，直接去session中取出即可
}
