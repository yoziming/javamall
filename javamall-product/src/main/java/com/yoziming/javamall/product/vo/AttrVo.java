package com.yoziming.javamall.product.vo;

import lombok.Data;

/**
 * @Author: yoziming
 * @Date: 2022/1/11 15:52
 * @Description:
 */
@Data
public class AttrVo {

    /**
     * 屬性id
     */
    private Long attrId;
    /**
     * 屬性名
     */
    private String attrName;
    /**
     * 是否需要檢索[0-不需要，1-需要]
     */
    private Integer searchType;
    /**
     * 屬性圖標
     */
    private String icon;
    /**
     * 值類型[0-為單個值，1-可以選擇多個值]
     */
    private Integer valueType;
    /**
     * 可選值列表[用逗號分隔]
     */
    private String valueSelect;
    /**
     * 屬性類型[0-銷售屬性，1-基本屬性，2-既是銷售屬性又是基本屬性]
     */
    private Integer attrType;
    /**
     * 啟用狀態[0 - 禁用，1 - 啟用]
     */
    private Long enable;
    /**
     * 所屬分類
     */
    private Long catalogId;
    /**
     * 快速展示【是否展示在介紹上；0-否 1-是】，在sku中仍然可以調整
     */
    private Integer showDesc;

    private Long attrGroupId;

}
