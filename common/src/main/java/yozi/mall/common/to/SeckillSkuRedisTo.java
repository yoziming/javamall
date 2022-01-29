package yozi.mall.common.to;

import lombok.Data;
import yozi.mall.common.vo.SkuInfoVo;

import java.math.BigDecimal;

@Data
public class SeckillSkuRedisTo {

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

    //sku的詳細信息
    private SkuInfoVo skuInfo;

    //當前商品秒殺的開始時間
    private Long startTime;

    //當前商品秒殺的結束時間
    private Long endTime;

    //當前商品秒殺的隨機碼
    private String randomCode;
}
