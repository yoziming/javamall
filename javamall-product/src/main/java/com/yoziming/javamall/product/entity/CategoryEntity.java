package com.yoziming.javamall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品三級分類
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
@Data
@TableName("t_product_category")
public class CategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分類id
     */
    @TableId
    private Long catId;
    /**
     * 分類名稱
     */
    private String name;
    /**
     * 父分類id
     */
    private Long parentCid;
    /**
     * 層級
     */
    private Integer catLevel;
    /**
     * 是否显示[0-不显示，1显示]
     */
    private Integer showStatus;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 圖標地址
     */
    private String icon;
    /**
     * 計量單位
     */
    private String productUnit;
    /**
     * 商品數量
     */
    private Integer productCount;

    /**
     * 所有子分類
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private List<CategoryEntity> children;
}
