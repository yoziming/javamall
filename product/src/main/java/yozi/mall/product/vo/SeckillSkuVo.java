package yozi.mall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeckillSkuVo {

    /**
     * 活動id
     */
    private Long promotionId;
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
     * 秒殺總量
     */
    private Integer seckillCount;
    /**
     * 每人限購數量
     */
    private Integer seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;

    //當前商品秒殺的開始時間
    private Long startTime;

    //當前商品秒殺的結束時間
    private Long endTime;

    //當前商品秒殺的隨機碼
    private String randomCode;

}
