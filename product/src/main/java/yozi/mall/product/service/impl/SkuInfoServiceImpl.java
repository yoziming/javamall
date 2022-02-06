package yozi.mall.product.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.product.dao.SkuInfoDao;
import yozi.mall.product.entity.SkuImagesEntity;
import yozi.mall.product.entity.SkuInfoEntity;
import yozi.mall.product.entity.SpuInfoDescEntity;
import yozi.mall.product.service.*;
import yozi.mall.product.vo.SkuItemSaleAttrVo;
import yozi.mall.product.vo.SkuItemVo;
import yozi.mall.product.vo.SpuItemAttrGroupVo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    AttrGroupService attrGroupService;
    @Autowired
    SkuImagesService skuImagesService;

    @Resource
    ThreadPoolExecutor executor;

    //    @Autowired
    //    SeckillFeignService seckillFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByKeyword(Map<String, Object> params) {
        // key: '華為',//檢索關鍵字
        // catalogId: 0,
        // brandId: 0,
        // min: 0,
        // max: 0
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key) && !"0".equalsIgnoreCase(key)) {
            wrapper.and(w -> {
                // 模糊搜索為了怕蓋掉下面的所以用and，其實也能放最後就好
                // 優先 NOT>AND>OR
                w.eq("sku_id", key).or().like("sku_name", key);
            });
        }
        String catalogId = (String) params.get("catalogId");
        if (StringUtils.isNotBlank(catalogId) && !"0".equalsIgnoreCase(catalogId)) {
            wrapper.eq("catalog_id", catalogId);
        }
        String brandId = (String) params.get("brandId");
        if (StringUtils.isNotBlank(brandId) && !"0".equalsIgnoreCase(brandId)) {
            wrapper.eq("brand_id", brandId);
        }
        String min = (String) params.get("min");
        if (StringUtils.isNotBlank(min) && new BigDecimal(min).compareTo(BigDecimal.ZERO) > 0) {
            wrapper.ge("price", min);
        }
        String max = (String) params.get("max");
        if (StringUtils.isNotBlank(max) && new BigDecimal(max).compareTo(BigDecimal.ZERO) > 0) {
            wrapper.le("price", max);
        }

        IPage<SkuInfoEntity> page = this.page(new Query<SkuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        return this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
    }

    // 商品詳情
    @Override
    public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {

        SkuItemVo skuItemVo = new SkuItemVo();

        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
            // 1、sku基本信息的獲取  pms_sku_info
            SkuInfoEntity info = this.getById(skuId);
            skuItemVo.setInfo(info);
            return info;
        }, executor);

        CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            // 3、獲取spu的銷售屬性組合
            List<SkuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrBySpuId(res.getSpuId());
            skuItemVo.setSaleAttr(saleAttrVos);
        }, executor);

        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync((res) -> {
            // 4、獲取spu的介紹    pms_spu_info_desc
            SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(res.getSpuId());
            skuItemVo.setDesc(spuInfoDescEntity);
        }, executor);

        CompletableFuture<Void> baseAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            // 5、獲取spu的規格參數信息
            List<SpuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(res.getSpuId(),
                    res.getCatalogId());
            skuItemVo.setGroupAttrs(attrGroupVos);
        }, executor);

        // 2、sku的圖片信息    pms_sku_images
        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            List<SkuImagesEntity> imagesEntities = skuImagesService.getImagesBySkuId(skuId);
            if (imagesEntities != null || ArrayUtil.isNotEmpty(imagesEntities)) {
                skuItemVo.setImages(imagesEntities);
            }
        }, executor);

        // // 3、查詢當前sku是否參與秒殺活動
        // CompletableFuture<Void> seckillFuture = CompletableFuture.runAsync(() -> {
        //     //3、遠程調用查詢當前sku是否參與秒殺優惠活動
        //     R skuSeckilInfo = seckillFeignService.getSkuSeckilInfo(skuId);
        //     if (skuSeckilInfo.getCode() == 0) {
        //         //查詢成功
        //         SeckillSkuVo seckilInfoData = skuSeckilInfo.getData("data", new TypeReference<SeckillSkuVo>() {
        //         });
        //         skuItemVo.setSeckillSkuVo(seckilInfoData);
        //
        //         if (seckilInfoData != null) {
        //             long currentTime = System.currentTimeMillis();
        //             if (currentTime > seckilInfoData.getEndTime()) {
        //                 skuItemVo.setSeckillSkuVo(null);
        //             }
        //         }
        //     }
        // }, executor);

        // 等到所有任務都完成
        CompletableFuture.allOf(saleAttrFuture, descFuture, baseAttrFuture, imageFuture).get();

        return skuItemVo;
    }

}