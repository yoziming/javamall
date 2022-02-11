package com.yoziming.javamall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * spu屬性值
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
@Data
@TableName("t_product_attr_value")
public class ProductAttrValueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 商品id
     */
    private Long spuId;
    /**
     * 屬性id
     */
    private Long attrId;
    /**
     * 屬性名
     */
    private String attrName;
    /**
     * 屬性值
     */
    private String attrValue;
    /**
     * 順序
     */
    private Integer attrSort;
    /**
     * 快速展示【是否展示在介紹上；0-否 1-是】
     */
    private Integer quickShow;

}
