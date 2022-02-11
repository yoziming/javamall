package com.yoziming.javamall.product.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/3/21 21:01
 */
@Data
public class SpuUpdateVo {
    private String spuName;
    private String spuDescription;
    private Long[] catalogId;
    private Long brandId;
    private BigDecimal weight;
    private int publishStatus;
    private List<String> decript;
    private List<String> images;
    private Bounds bounds;
    private List<BaseAttrs> baseAttrs;
    private List<Skus> skus;

}
