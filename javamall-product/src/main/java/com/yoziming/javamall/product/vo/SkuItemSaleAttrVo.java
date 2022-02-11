package com.yoziming.javamall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/2/3 14:07
 * @Description:
 */
@ToString
@Data
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}
