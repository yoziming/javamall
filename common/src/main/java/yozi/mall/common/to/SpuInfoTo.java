package yozi.mall.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SpuInfoTo {

    /**
     * 商品id
     */
    private Long id;
    /**
     * 商品名稱
     */
    private String spuName;
    /**
     * 商品描述
     */
    private String spuDescription;
    /**
     * 所屬分類id
     */
    private Long catalogId;
    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 品牌名
     */
    private String brandName;

    /**
     *
     */
    private BigDecimal weight;
    /**
     * 上架狀態[0 - 下架，1 - 上架]
     */
    private Integer publishStatus;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

}
