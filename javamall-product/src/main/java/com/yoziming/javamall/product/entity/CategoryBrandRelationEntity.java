package com.yoziming.javamall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌分類關聯
 *
 * @author yoziming
 * @date 2021-01-10 22:30:47
 */
@Data
@TableName("t_product_category_brand_relation")
public class CategoryBrandRelationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 分類id
     */
    private Long catalogId;
    /**
     *
     */
    private String brandName;
    /**
     *
     */
    private String catalogName;

}
