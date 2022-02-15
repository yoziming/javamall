package com.yoziming.javamall.product.vo;

import com.yoziming.javamall.product.entity.*;
import lombok.Data;

import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/2/3 14:07
 * @Description:
 */
@Data
public class SkuItemVo {
    //1、sku基本訊息獲取 pms_sku_info
    SkuInfoEntity info;
    // 誰有空給每個sku做介紹，直接獲取spu info spu_description
    SpuInfoEntity spuInfo;

    boolean hasStock = false;
    boolean isPublish = false;
    //2、sku的圖片訊息 pms_sku_images
    List<SkuImagesEntity> images;
    //3、獲取spu的銷售屬性組合。
    List<SkuItemSaleAttrVo> saleAttr;
    //4、獲取spu的介紹 大圖
    SpuInfoDescEntity desp;
    //5、獲取spu的規格參數訊息
    List<SpuItemAttrGroupVo> groupAttrs;
    // //spu的圖片 t_product_spu_images
    // List<SpuImagesEntity> spuImages;
    // 品牌
    BrandEntity brand;
    // 分類
    List<CategoryEntity> categorys;
}
