package yozi.mall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yozi.mall.common.constant.ProductConstant;
import yozi.mall.common.es.SkuEsModel;
import yozi.mall.common.feign.CouponFeignService;
import yozi.mall.common.feign.SearchFeignService;
import yozi.mall.common.feign.WareFeignService;
import yozi.mall.common.to.SkuHasStockTo;
import yozi.mall.common.to.SkuReductionTo;
import yozi.mall.common.to.SpuBoundsTo;
import yozi.mall.common.to.SpuInfoTo;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.common.utils.R;
import yozi.mall.product.dao.SpuInfoDao;
import yozi.mall.product.entity.*;
import yozi.mall.product.service.*;
import yozi.mall.product.vo.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    private CouponFeignService couponFeignService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WareFeignService wareFeignService;

    @Autowired
    private SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        // 1、保存spu基本信息：pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, spuInfoEntity);
        // 如果沒指定狀態就是新建
        // Integer publishStatus = vo.getPublishStatus();
        // if (publishStatus == null) {
        //     spuInfoEntity.setPublishStatus(0);
        // }
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.save(spuInfoEntity);

        // 2、保存spu的描述圖片：pms_spu_info_desc
        // 這個decript錯字來自前端...算了就照它的吧
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        // 用逗號分隔多張描述圖片，描述圖片就是購物時左上那些摸過去會放大的小圖
        // String.join方法可以自動遍歷數組中的String然後拼接成一個String
        spuInfoDescEntity.setDecript(String.join(",", decript));
        spuInfoDescService.save(spuInfoDescEntity);

        // 3、保存spu的圖片集：pms_spu_images，
        // 圖片集就是往下拉，在頁面中央出現的多張商品宣傳大圖
        List<String> images = vo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(), images);

        // 4、保存spu的規格參數：pms_product_attr_value
        List<BaseAttrsItem> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
            valueEntity.setAttrId(attr.getAttrId());

            // 查詢attr屬性名
            AttrEntity byId = attrService.getById(attr.getAttrId());

            valueEntity.setAttrName(byId.getAttrName());
            valueEntity.setAttrValue(attr.getAttrValues());
            valueEntity.setQuickShow(attr.getShowDesc());
            valueEntity.setSpuId(spuInfoEntity.getId());
            return valueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveBatch(collect);

        // 6、保存spu的積分信息：mall_sms--->sms_spu_bounds
        Bounds bounds = vo.getBounds();
        SpuBoundsTo spuBoundsTo = new SpuBoundsTo();
        BeanUtils.copyProperties(bounds, spuBoundsTo);
        spuBoundsTo.setSpuId(spuInfoEntity.getId());
        R r = couponFeignService.saveSpuBounds(spuBoundsTo);

        if (r.getCode() != 0) {
            log.error("遠程保存spu積分失敗");
        }

        // 5、保存當前spu對應的所有sku信息：pms_sku_info
        List<SkusItem> skus = vo.getSkus();
        if (skus != null && skus.size() > 0) {
            skus.forEach(item -> {
                // 傳來的是多個skus，遍歷一個一個處理

                // 5、1）、sku的基本信息:pms_sku_info
                // 如果有設定預設圖片
                String defaultImg = "";
                for (ImagesItem image : item.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }

                // 準備存到pms_sku_info表
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                // 資料庫中又是這個錯字CatalogId，之前被坑好久
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);

                skuInfoService.save(skuInfoEntity);

                Long skuId = skuInfoEntity.getSkuId();
                // 5、2）、sku的圖片信息：pms_sku_images，這張表中的條目是基於skuId
                // 因為一個skuId可以有多張圖片
                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity -> {
                    // filter返回true就是需要留下的，false就是剔除的
                    return StringUtils.isNotBlank(entity.getImgUrl());
                }).collect(Collectors.toList());

                // 批量保存到pms_sku_images
                skuImagesService.saveBatch(imagesEntities);

                // 5、3）、sku的銷售屬性：pms_sku_sale_attr_value
                // 就是因為銷售屬性的笛卡爾積導致有多個sku，這張表保存了某個skuId對應的屬性具體是啥值
                // 例如顏色是黑色，RAM是8G
                List<AttrItem> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(a -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(a, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                // 批量保存到pms_sku_sale_attr_value
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                // 5、4）、sku的優惠、滿減等信息：mall_sms--->sms_sku_ladder、sms_sku_full_reduction、sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                // 有設定打折規則再保存，省得資料庫中存一堆0
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(BigDecimal.ZERO) == 1) {
                    R r1 = couponFeignService.saveSkuReduction(skuReductionTo);
                    if (r1.getCode() != 0) {
                        log.error("遠程保存sku優惠資訊失敗");
                    }
                }
            });
        }
    }

    @Override
    public PageUtils queryPageByKeyword(Map<String, Object> params) {
   /*
   key: '華為',//檢索關鍵字
   catalogId: 6,//三級分類id
   brandId: 1,//品牌id
   status: 0,//商品狀態
    */
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key) && !"0".equalsIgnoreCase(key)) {
            wrapper.and(w -> {
                // 模糊搜索為了怕蓋掉下面的所以用and，其實也能放最後就好
                // 優先 NOT>AND>OR
                w.eq("id", key).or().like("spu_name", key);
            });
        }
        String status = (String) params.get("status");
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq("publish_status", status);
        }
        String catalogId = (String) params.get("catalogId");
        if (StringUtils.isNotBlank(catalogId) && !"0".equalsIgnoreCase(catalogId)) {
            wrapper.eq("catalog_id", catalogId);
        }
        String brandId = (String) params.get("brandId");
        if (StringUtils.isNotBlank(brandId) && !"0".equalsIgnoreCase(brandId)) {
            wrapper.eq("brand_id", brandId);
        }

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params), wrapper
        );
        return new PageUtils(page);
    }

    /**
     * 根據skuId查詢spu的信息
     *
     * @param skuId
     * @return
     */
    @Override
    public SpuInfoTo getSpuInfoBySkuId(Long skuId) {

        //先查詢sku表裡的數據
        SkuInfoEntity skuInfoEntity = skuInfoService.getById(skuId);

        //獲得spuId
        Long spuId = skuInfoEntity.getSpuId();

        //再通過spuId查詢spuInfo信息表裡的數據
        SpuInfoEntity spuInfoEntity = baseMapper.selectById(spuId);

        SpuInfoTo spuInfoTo = new SpuInfoTo();
        BeanUtils.copyProperties(spuInfoEntity, spuInfoTo);

        //查詢品牌表的數據獲取品牌名
        BrandEntity brandEntity = brandService.getById(spuInfoEntity.getBrandId());
        spuInfoTo.setBrandName(brandEntity.getName());

        return spuInfoTo;
    }

    // 商品上架
    @Override
    public void up(Long spuId) {
        // 先從spuID查出對應的所有sku
        List<SkuInfoEntity> skuInfoEntities = skuInfoService.getSkusBySpuId(spuId);

        // 先查出當前sku的所有可以被用來檢索的規格屬性
        List<ProductAttrValueEntity> baseAttrs = productAttrValueService.baseAttrListforspu(spuId);
        List<Long> attrIds = baseAttrs.stream().map(ProductAttrValueEntity::getAttrId).collect(Collectors.toList());
        // sku當初填值時就有一欄是"是否能被檢索
        List<Long> searchAttrIds = attrService.selectSearchAttrs(attrIds);
        // 轉換為Set集合
        Set<Long> idSet = searchAttrIds.stream().collect(Collectors.toSet());
        // 過濾留下能被檢索的Attrs
        List<SkuEsModel.Attrs> attrsList = baseAttrs.stream().filter(item -> {
            return idSet.contains(item.getAttrId());
        }).map(item -> {
            // 封裝進SkuEsModel.Attrs
            SkuEsModel.Attrs attrs = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attrs);
            return attrs;
        }).collect(Collectors.toList());

        // 接著查是否有庫存，先把ID抽成list方便一次查完
        List<Long> skuIdList = skuInfoEntities.stream()
                .map(SkuInfoEntity::getSkuId)
                .collect(Collectors.toList());
        // 發送遠程調用，庫存系統查詢是否有庫存
        Map<Long, Boolean> stockMap = null;
        try {
            R skuHasStock = wareFeignService.getSkuHasStock(skuIdList);
            TypeReference<List<SkuHasStockTo>> typeReference = new TypeReference<List<SkuHasStockTo>>() {
            };
            // 把查詢結果拿出來，裝到Map<Long, Boolean>
            stockMap = skuHasStock.getData(typeReference).stream().collect(Collectors.toMap(SkuHasStockTo::getSkuId,
                    SkuHasStockTo::getHasStock));

        } catch (Exception e) {
            log.error("庫存服務查詢異常：原因{}", e);
        }
        Map<Long, Boolean> finalStockMap = stockMap;
        // 要傳去ES的商品訊息
        List<SkuEsModel> collect = skuInfoEntities.stream().map(sku -> {
            // 組裝需要的數據
            SkuEsModel esModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, esModel);
            // 其他對不上，需要手動添加的
            esModel.setSkuPrice(sku.getPrice());
            esModel.setSkuImg(sku.getSkuDefaultImg());

            // 設置庫存信息，如果遠程調用失敗就先當作有
            if (finalStockMap == null) {
                esModel.setHasStock(true);
            } else {
                esModel.setHasStock(finalStockMap.get(sku.getSkuId()));
            }
            // TODO 2、熱度評分。0
            esModel.setHotScore(0L);
            // 查詢品牌和分類的名字信息
            BrandEntity brandEntity = brandService.getById(sku.getBrandId());
            esModel.setBrandName(brandEntity.getName());
            esModel.setBrandId(brandEntity.getBrandId());
            esModel.setBrandImg(brandEntity.getLogo());

            CategoryEntity categoryEntity = categoryService.getById(sku.getCatalogId());
            esModel.setCatalogId(categoryEntity.getCatId());
            esModel.setCatalogName(categoryEntity.getName());

            // 設置子屬性，來自前面查的列表
            esModel.setAttrs(attrsList);
            return esModel;
        }).collect(Collectors.toList());

        // 發到ES
        R r = searchFeignService.productStatusUp(collect);

        if (r.getCode() == 0) {
            // 遠程調用成功
            // 修改當前spu的狀態
            this.baseMapper.updateSpuStatus(spuId, ProductConstant.ProductStatusEnum.SPU_UP.getCode());
        } else {
            //遠程調用失敗
            //TODO 7、重複調用？接口冪等性:重試機制
        }
    }
}