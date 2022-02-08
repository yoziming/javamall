package yoziming.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.product.entity.CategoryEntity;
import yoziming.mall.product.vo.Catalogs2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三級分類
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listTree();

    void removeMenuByIds(List<Long> asList);

    Long[] findCatalogPath(Long catalogId);

    void updateCascade(CategoryEntity category);

    List<CategoryEntity> getLevel1Catalog();

    Map<String, List<Catalogs2Vo>> getCatalogJson();
}

