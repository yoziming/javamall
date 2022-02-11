package com.yoziming.javamall.product.vo;

import com.yoziming.javamall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/1/13 15:13
 * @Description:
 */
@Data
public class AttrGroupWithAttrsVo {

    /**
     * 分組id
     */
    private Long attrGroupId;
    /**
     * 組名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 組圖標
     */
    private String icon;
    /**
     * 所屬分類id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;
}

