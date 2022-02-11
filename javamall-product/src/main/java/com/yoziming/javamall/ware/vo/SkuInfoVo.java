package com.yoziming.javamall.ware.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: yoziming
 * @Date: 2022/3/19 16:53
 * @Description:
 */
@Data
public class SkuInfoVo implements Serializable {

    /**
     * skuId
     */
    @TableId
    private Long skuId;
    /**
     * spuId
     */
    private Long spuId;
    /**
     * sku名稱
     */
    private String skuName;
    /**
     * sku介紹描述
     */
    private String skuDesc;
    /**
     * 所屬分類id
     */
    private Long catalogId;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 預設圖片
     */
    private String skuDefaultImg;
    /**
     * 標題
     */
    private String skuTitle;
    /**
     * 副標題
     */
    private String skuSubtitle;
    /**
     * 價格
     */
    private BigDecimal price;
    /**
     * 銷量
     */
    private Long saleCount;

}
