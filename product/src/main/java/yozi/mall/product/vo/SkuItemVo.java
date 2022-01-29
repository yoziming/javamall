package yozi.mall.product.vo;

import lombok.Data;
import yozi.mall.product.entity.SkuImagesEntity;
import yozi.mall.product.entity.SkuInfoEntity;
import yozi.mall.product.entity.SpuInfoDescEntity;

import java.util.List;

@Data
public class SkuItemVo {
    /**
     * 1、sku基本信息【標題、副標題、價格】pms_sku_info
     * 2、sku圖片信息【每個sku_id對應了多個圖片】pms_sku_images
     * 3、spu下所有sku銷售屬性組合【不只是當前sku_id所指定的商品】
     * 4、spu商品介紹【】
     * 5、spu規格與包裝【參數信息】
     */

    //1、sku基本信息的獲取  pms_sku_info
    private SkuInfoEntity info;

    private boolean hasStock = true;

    //2、sku的圖片信息    pms_sku_images
    private List<SkuImagesEntity> images;

    //3、獲取spu的銷售屬性組合【一個vo是一個銷售屬性】
    private List<SkuItemSaleAttrVo> saleAttr;

    //4、獲取spu的介紹
    private SpuInfoDescEntity desc;

    //5、獲取spu的規格參數信息【以組為單位】
    private List<SpuItemAttrGroupVo> groupAttrs;

    //6、秒殺商品的優惠信息
    private SeckillSkuVo seckillSkuVo;

}
