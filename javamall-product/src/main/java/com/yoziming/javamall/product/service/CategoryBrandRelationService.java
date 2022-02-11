package com.yoziming.javamall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.product.entity.BrandEntity;
import com.yoziming.javamall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分類關聯
 *
 * @author yoziming
 * @date 2021-01-10 22:30:47
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    List<BrandEntity> getBrandsByCatId(Long catId);

    void updateBrand(Long brandId, String name);

    void updateCategory(Long catId, String name);
}

