package com.yoziming.javamall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.to.SkuHasStockVo;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;
import com.yoziming.common.utils.R;
import com.yoziming.javamall.product.dao.SkuInfoDao;
import com.yoziming.javamall.product.entity.*;
import com.yoziming.javamall.product.service.*;
import com.yoziming.javamall.product.vo.SkuItemSaleAttrVo;
import com.yoziming.javamall.product.vo.SkuItemVo;
import com.yoziming.javamall.product.vo.SpuItemAttrGroupVo;
import com.yoziming.javamall.ware.controller.WareSkuController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    SkuImagesService skuImagesService;

    @Autowired
    SpuInfoDescService spuInfoDescService;

    @Autowired
    SpuInfoService spuInfoService;

    @Autowired
    AttrGroupService attrGroupService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    WareSkuController wareFeignService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> queryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key) && !"0".equalsIgnoreCase(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq("sku_id", key).or().like("sku_name", key);
            });
        }

        String catelogId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            queryWrapper.eq("catalog_id", catelogId);
        }

        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)) {
            queryWrapper.eq("brand_id", brandId);
        }

        String min = (String) params.get("min");
        if (!StringUtils.isEmpty(min)) {
            queryWrapper.ge("price", min);
        }

        String max = (String) params.get("max");

        if (!StringUtils.isEmpty(max)) {
            try {
                BigDecimal bigDecimal = new BigDecimal(max);
                if (bigDecimal.compareTo(BigDecimal.ZERO) == 1) {
                    queryWrapper.le("price", max);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // key:
        // catelogId: 225
        // brandId: 9
        // min: 0
        // max: 0

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);

    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        List<SkuInfoEntity> skuInfoEntities = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));

        return skuInfoEntities;

    }

    @Override
    public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {
        SkuItemVo skuItemVo = new SkuItemVo();
        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
            //1、sku基本訊息獲取 pms_sku_info
            SkuInfoEntity info = getById(skuId);
            skuItemVo.setInfo(info);
            return info;
        }, threadPoolExecutor);
        // 獲取品牌訊息
        CompletableFuture<Void> brandFuture = infoFuture.thenAcceptAsync((res) -> {
            BrandEntity brandEntity = brandService.getById(res.getBrandId());
            skuItemVo.setBrand(brandEntity);
            List<CategoryEntity> categoryByIds = categoryService.getCategoryByIds(res.getCatalogId());
            skuItemVo.setCategorys(categoryByIds);
        }, threadPoolExecutor);
        CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            //3、獲取spu的銷售屬性組合。
            List<SkuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrsBySpuId(res.getSpuId());
            skuItemVo.setSaleAttr(saleAttrVos);
        }, threadPoolExecutor);
        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync((res) -> {
            //4、獲取spu的介紹
            QueryWrapper<SpuInfoDescEntity> spuInfoDescEntityQueryWrapper = new QueryWrapper<SpuInfoDescEntity>().eq(
                    "spu_id", res.getSpuId());
            SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getOne(spuInfoDescEntityQueryWrapper);
            skuItemVo.setDesp(spuInfoDescEntity);
            boolean publish = spuInfoService.isPublish(res.getSpuId());
            skuItemVo.setPublish(publish);
        }, threadPoolExecutor);
        CompletableFuture<Void> baseAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            //5、獲取spu的規格參數訊息
            List<SpuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(res.getSpuId(),
                    res.getCatalogId());
            skuItemVo.setGroupAttrs(attrGroupVos);
        }, threadPoolExecutor);

        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            //2、sku的圖片訊息 pms_sku_images
            List<SkuImagesEntity> images = skuImagesService.getImagesBySkuId(skuId);
            skuItemVo.setImages(images);
        }, threadPoolExecutor);
        //是否有貨
        CompletableFuture<Void> hasStockFuture = CompletableFuture.runAsync(() -> {
            R skuHasStock = wareFeignService.getSkuHasStock(Arrays.asList(skuId));
            List<SkuHasStockVo> hasStockData = skuHasStock.getData(new TypeReference<List<SkuHasStockVo>>() {
            });
            skuItemVo.setHasStock(hasStockData.get(0).getHasStock());
        }, threadPoolExecutor);
        CompletableFuture.allOf(saleAttrFuture, descFuture, baseAttrFuture, imageFuture, hasStockFuture, brandFuture).get();
        CompletableFuture<Void> isPublishFuture = CompletableFuture.runAsync(() -> {

        }, threadPoolExecutor);
        return skuItemVo;
    }

}