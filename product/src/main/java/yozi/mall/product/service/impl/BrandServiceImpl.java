package yozi.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.product.dao.BrandDao;
import yozi.mall.product.entity.BrandEntity;
import yozi.mall.product.service.BrandService;
import yozi.mall.product.service.CategoryBrandRelationService;

import java.util.List;
import java.util.Map;

@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 模糊查詢
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> qw = new QueryWrapper<>();
        if (StringUtils.isNotBlank(key)) {
            qw.and((obj) -> {
                obj.eq("brand_id", key).or().like("name", key);
            });
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params), qw);

        return new PageUtils(page);
    }

    /**
     * 冗餘的品牌名、品項名更新
     *
     * @param brand
     */
    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        // 先更新自己
        this.updateById(brand);
        // 如果有更動品牌名
        if (StringUtils.isNotBlank(brand.getName())) {
            // 更新資料庫表中所有舊的品牌名
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());
        }
        // TODO 更新其他關聯

    }

    @Override
    public List<BrandEntity> getBrandsByIds(List<Long> brandIds) {
        return baseMapper.selectList(new QueryWrapper<BrandEntity>().in("brand_id", brandIds));
    }

}