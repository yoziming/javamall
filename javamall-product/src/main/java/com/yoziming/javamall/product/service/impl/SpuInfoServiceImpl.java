package com.yoziming.javamall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.constant.ProductConstant;
import com.yoziming.common.es.SkuEsModel;
import com.yoziming.common.to.SkuHasStockVo;
import com.yoziming.common.to.SpuBoundTo;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;
import com.yoziming.common.utils.R;
import com.yoziming.javamall.config.ElasticsearchConfig;
import com.yoziming.javamall.product.dao.SpuInfoDao;
import com.yoziming.javamall.product.entity.*;
import com.yoziming.javamall.product.service.*;
import com.yoziming.javamall.product.vo.*;
import com.yoziming.javamall.search.constant.EsConstant;
import com.yoziming.javamall.search.controller.ElasticSaveController;
import com.yoziming.javamall.ware.controller.WareSkuController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WareSkuController wareFeignService;

    //    @Autowired
    //    private SearchFeignService searchFeignService;

    @Autowired
    private ElasticSaveController elasticSaveController;

    @Autowired
    private RestHighLevelClient esRestClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }

    /**
     * 高級部分完善後續
     *
     * @param vo 新增商品
     */
    //    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSupInfo(SpuSaveVo vo) {

        //1、保存spu基本訊息：pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);

        //2、保存spu的描述圖片：pms_spu_info_desc
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",", decript));
        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);

        //3、保存spu的圖片集：pms_spu_images
        List<String> images = vo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(), images);

        //4、保存spu的規格參數：pms_product_attr_value
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
            valueEntity.setAttrId(attr.getAttrId());

            //查詢attr屬性名
            AttrEntity byId = attrService.getById(attr.getAttrId());

            valueEntity.setAttrName(byId.getAttrName());
            valueEntity.setAttrValue(attr.getAttrValues());
            valueEntity.setQuickShow(attr.getShowDesc());
            valueEntity.setSpuId(spuInfoEntity.getId());
            return valueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveProductAttr(collect);

        //5、保存spu的積分訊息：sms_spu_bounds
        Bounds bounds = vo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds, spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        //        R r = couponFeignService.saveSpuBounds(spuBoundTo);
        //
        //        if (r.getCode() != 0) {
        //            log.error("遠程保存spu積分訊息失敗");
        //        }

        //5、保存當前spu對應的所有sku訊息：pms_sku_info
        //5、1）、sku的基本訊息:pms_sku_info
        List<Skus> skus = vo.getSkus();
        if (skus != null && skus.size() > 0) {
            skus.forEach(item -> {
                String defaultImg = "";
                for (Images image : item.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }

                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                skuInfoService.saveSkuInfo(skuInfoEntity);

                Long skuId = skuInfoEntity.getSkuId();

                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity -> {
                    //返回true就是需要，false就是剔除
                    return !StringUtils.isEmpty(entity.getImgUrl());
                }).collect(Collectors.toList());

                //5、2）、sku的圖片訊息：pms_sku_images
                skuImagesService.saveBatch(imagesEntities);

                //5、3）、sku的銷售屬性：pms_sku_sale_attr_value
                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(a -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(a, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());

                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

            });
        }
    }

    @Override
    public SpuUpdateVo getSpu(Long id) {
        SpuUpdateVo spuSaveVo = new SpuUpdateVo();
        // == spu info
        SpuInfoEntity spuInfoEntity = this.getById(id);
        spuSaveVo.setSpuName(spuInfoEntity.getSpuName());
        spuSaveVo.setSpuDescription(spuInfoEntity.getSpuDescription());

        Long[] catalogPath = categoryService.findCatalogPath(spuInfoEntity.getCatalogId());
        spuSaveVo.setCatalogId(catalogPath);

        spuSaveVo.setBrandId(spuInfoEntity.getBrandId());
        spuSaveVo.setWeight(spuInfoEntity.getWeight());
        spuSaveVo.setPublishStatus(spuInfoEntity.getPublishStatus());
        // == spu desc
        SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getOne(new QueryWrapper<SpuInfoDescEntity>().eq(
                "spu_id", id));
        List<String> decript = Arrays.asList(StringUtils.split(spuInfoDescEntity.getDecript(), ','));
        spuSaveVo.setDecript(decript);
        // == spu img
        QueryWrapper<SpuImagesEntity> spuImagesEntityQueryWrapper = new QueryWrapper<>();
        spuImagesEntityQueryWrapper.in("spu_id", id);
        List<SpuImagesEntity> spuImagesEntityList = spuImagesService.list(spuImagesEntityQueryWrapper);
        List<String> imagesList = new ArrayList<>();
        for (SpuImagesEntity imagesEntity : spuImagesEntityList) {
            imagesList.add(imagesEntity.getImgUrl());
        }
        spuSaveVo.setImages(imagesList);
        // == bounds
        Bounds bounds = new Bounds();
        bounds.setBuyBounds(BigDecimal.ZERO);
        bounds.setGrowBounds(BigDecimal.ZERO);
        spuSaveVo.setBounds(bounds);
        // == spu attr
        QueryWrapper<ProductAttrValueEntity> productAttrValueEntityQueryWrapper = new QueryWrapper<>();
        productAttrValueEntityQueryWrapper.eq("spu_id", id);
        List<ProductAttrValueEntity> productAttrValueEntities =
                productAttrValueService.list(productAttrValueEntityQueryWrapper);
        List<BaseAttrs> baseAttrsList = new ArrayList<>();
        for (ProductAttrValueEntity entity : productAttrValueEntities) {
            BaseAttrs baseAttrs = new BaseAttrs();
            baseAttrs.setAttrId(entity.getAttrId());
            baseAttrs.setAttrValues(entity.getAttrValue());
            baseAttrs.setShowDesc(entity.getQuickShow());
            baseAttrsList.add(baseAttrs);
        }
        spuSaveVo.setBaseAttrs(baseAttrsList);
        // == sku
        QueryWrapper<SkuInfoEntity> skuInfoEntityQueryWrapper = new QueryWrapper<>();
        skuInfoEntityQueryWrapper.eq("spu_id", id);
        List<SkuInfoEntity> skuInfoEntityList = skuInfoService.list(skuInfoEntityQueryWrapper);

        List<Long> skuIdList = skuInfoEntityList.stream().map((skuInfoEntity) -> {
            return skuInfoEntity.getSkuId();
        }).collect(Collectors.toList());
        QueryWrapper<SkuImagesEntity> skuImagesEntityQueryWrapper = new QueryWrapper<>();
        skuImagesEntityQueryWrapper.in("sku_id", skuIdList);
        List<SkuImagesEntity> skuImagesEntityList = skuImagesService.list(skuImagesEntityQueryWrapper);

        QueryWrapper<SkuSaleAttrValueEntity> skuSaleAttrValueEntityQueryWrapper = new QueryWrapper<>();
        skuSaleAttrValueEntityQueryWrapper.in("sku_id", skuIdList);
        List<SkuSaleAttrValueEntity> skuSaleAttrValueEntityList =
                skuSaleAttrValueService.list(skuSaleAttrValueEntityQueryWrapper);

        List<Skus> skusList = skuInfoEntityList.stream().map(sku -> {
            Skus skus = new Skus();

            List<Attr> attrList = new ArrayList<>();
            for (SkuSaleAttrValueEntity skuAttr : skuSaleAttrValueEntityList) {
                if (skuAttr.getSkuId().equals(sku.getSkuId())) {
                    Attr attr = new Attr();
                    attr.setAttrId(skuAttr.getAttrId());
                    attr.setAttrName(skuAttr.getAttrName());
                    attr.setAttrValue(skuAttr.getAttrValue());
                    attrList.add(attr);
                }
            }
            skus.setAttr(attrList);

            List<Images> imgList = new ArrayList<>();
            for (SkuImagesEntity skuImg : skuImagesEntityList) {
                if (skuImg.getSkuId().equals(sku.getSkuId())) {
                    Images images = new Images();
                    images.setImgUrl(skuImg.getImgUrl());
                    images.setDefaultImg(skuImg.getDefaultImg());
                    imgList.add(images);
                }
            }
            skus.setImages(imgList);

            skus.setSkuName(sku.getSkuName());
            skus.setPrice(sku.getPrice());
            skus.setSkuTitle(sku.getSkuTitle());
            skus.setSkuSubtitle(sku.getSkuSubtitle());

            return skus;
        }).collect(Collectors.toList());

        spuSaveVo.setSkus(skusList);

        return spuSaveVo;
    }

    @Transactional
    @Override
    public void removeSpu(Long id) {
        this.removeById(id);
        QueryWrapper<SkuInfoEntity> skuWrapper = new QueryWrapper<>();
        skuWrapper.eq("spu_id", id);
        skuInfoService.remove(skuWrapper);
    }

    @Override
    public boolean isPublish(Long id) {
        SpuInfoEntity byId = this.getById(id);
        return byId.getPublishStatus() == 1 ? true : false;
    }

    @Override
    public PageUtils queryPageByCondtion(Map<String, Object> params) {

        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq("id", key).or().like("spu_name", key);
            });
        }

        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("publish_status", status);
        }

        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)) {
            queryWrapper.eq("brand_id", brandId);
        }

        String catalogId = (String) params.get("catalogId");
        if (!StringUtils.isEmpty(catalogId) && !"0".equalsIgnoreCase(catalogId)) {
            queryWrapper.eq("catalog_id", catalogId);
        }

        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(params), queryWrapper);

        return new PageUtils(page);
    }

    //    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void up(Long spuId) {

        //1、查出當前spuId對應的所有sku訊息,品牌的名字
        List<SkuInfoEntity> skuInfoEntities = skuInfoService.getSkusBySpuId(spuId);

        //4、查出當前sku的所有可以被用來檢索的規格屬性
        List<ProductAttrValueEntity> baseAttrs = productAttrValueService.baseAttrListforspu(spuId);

        List<Long> attrIds = baseAttrs.stream().map(attr -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        List<Long> searchAttrIds = attrService.selectSearchAttrs(attrIds);
        //轉換為Set集合
        Set<Long> idSet = searchAttrIds.stream().collect(Collectors.toSet());

        List<SkuEsModel.Attrs> attrsList = baseAttrs.stream().filter(item -> {
            return idSet.contains(item.getAttrId());
        }).map(item -> {
            SkuEsModel.Attrs attrs = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attrs);
            return attrs;
        }).collect(Collectors.toList());

        List<Long> skuIdList = skuInfoEntities.stream()
                .map(SkuInfoEntity::getSkuId)
                .collect(Collectors.toList());
        //發送遠程調用，庫存系統查詢是否有庫存
        Map<Long, Boolean> stockMap = null;
        try {
            R skuHasStock = wareFeignService.getSkuHasStock(skuIdList);
            //
            TypeReference<List<SkuHasStockVo>> typeReference = new TypeReference<List<SkuHasStockVo>>() {
            };
            stockMap = skuHasStock.getData(typeReference).stream()
                    .collect(Collectors.toMap(SkuHasStockVo::getSkuId, item -> item.getHasStock()));
        } catch (Exception e) {
            log.error("庫存服務查詢異常：原因{}", e);
        }

        //2、封裝每個sku的訊息
        Map<Long, Boolean> finalStockMap = stockMap;
        List<SkuEsModel> collect = skuInfoEntities.stream().map(sku -> {
            //組裝需要的數據
            SkuEsModel esModel = new SkuEsModel();
            esModel.setSkuPrice(sku.getPrice());
            esModel.setSkuImg(sku.getSkuDefaultImg());

            //設置庫存訊息
            if (finalStockMap == null) {
                esModel.setHasStock(true);
            } else {
                esModel.setHasStock(finalStockMap.get(sku.getSkuId()));
            }

            //2、熱度評分。0
            esModel.setHotScore(0L);

            //3、查詢品牌和分類的名字訊息
            BrandEntity brandEntity = brandService.getById(sku.getBrandId());
            esModel.setBrandName(brandEntity.getName());
            esModel.setBrandId(brandEntity.getBrandId());
            esModel.setBrandImg(brandEntity.getLogo());

            CategoryEntity categoryEntity = categoryService.getById(sku.getCatalogId());
            esModel.setCatalogId(categoryEntity.getCatId());
            esModel.setCatalogName(categoryEntity.getName());

            //設置檢索屬性
            esModel.setAttrs(attrsList);

            BeanUtils.copyProperties(sku, esModel);

            return esModel;
        }).collect(Collectors.toList());

        //5、將數據發給es進行保存：javamall-search
        R r = elasticSaveController.productStatusUp(collect);

        if (r.getCode() == 0) {
            //遠程調用成功
            //修改當前spu的狀態
            this.baseMapper.updaSpuStatus(spuId, ProductConstant.ProductStatusEnum.SPU_UP.getCode());
        } else {
            //遠程調用失敗
            //重複調用？接口冪等性:重試機制
            log.error("searchFeignService：遠程調用失敗");
        }
    }

    @Override
    public SpuInfoEntity getSpuInfoBySkuId(Long skuId) {
        SkuInfoEntity byId = skuInfoService.getById(skuId);
        Long spuId = byId.getSpuId();
        return getById(spuId);
    }

    /**
     * 下架
     *
     * @param spuId
     */
    @Override
    @Transactional
    public void down(Long spuId) {
        try {
            // 刪除es
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            // 根據spuid查出所有sku
            boolQueryBuilder.must(QueryBuilders.matchQuery("spuId", spuId));
            searchSourceBuilder.query(boolQueryBuilder);

            log.info("下架商品構建的DSL語句={}", searchSourceBuilder.toString());

            SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX},
                    searchSourceBuilder);
            SearchResponse response = esRestClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);

            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                String id = hit.getId();
                DeleteRequest deleteRequest = new DeleteRequest(EsConstant.PRODUCT_INDEX, id);
                esRestClient.delete(deleteRequest, ElasticsearchConfig.COMMON_OPTIONS);
            }
        } catch (IOException e) {
            log.error("es：IO鏈接異常！", e);
        } catch (Exception e) {
            log.error("es：異常！", e);
        }
        // 修改資料庫spu狀態
        UpdateWrapper<SpuInfoEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", spuId)
                .set("publish_status", 2);
        this.update(updateWrapper);
    }

}