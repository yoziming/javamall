package yoziming.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.Query;
import yoziming.mall.product.dao.CategoryBrandRelationDao;
import yoziming.mall.product.entity.BrandEntity;
import yoziming.mall.product.entity.CategoryBrandRelationEntity;
import yoziming.mall.product.service.BrandService;
import yoziming.mall.product.service.CategoryBrandRelationService;
import yoziming.mall.product.service.CategoryService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao,
        CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catalogId = categoryBrandRelation.getCatalogId();

        // 為了避免用資料庫關聯，用冗查詢去找對應的品牌名、品項名
        categoryBrandRelation.setBrandName(brandService.getById(brandId).getName());
        categoryBrandRelation.setCatalogName(categoryService.getById(catalogId).getName());
        this.save(categoryBrandRelation);
    }

    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandId(brandId);
        categoryBrandRelationEntity.setBrandName(name);
        // 更新資料庫表中所有舊的品牌名
        this.update(categoryBrandRelationEntity, new UpdateWrapper<CategoryBrandRelationEntity>()
                .eq("brand_id", brandId));

    }

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId, name);

    }

    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        List<CategoryBrandRelationEntity> relationEntities =
                baseMapper.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catalog_id", catId));
        List<BrandEntity> collect = relationEntities.stream().map(e -> {
            Long brandId = e.getBrandId();
            // 調用Service而非DAO，因為DAO是生成的最好寫死的
            return brandService.getById(brandId);
        }).collect(Collectors.toList());
        return collect;
    }
}