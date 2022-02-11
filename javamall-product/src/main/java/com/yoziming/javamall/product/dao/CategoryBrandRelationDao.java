package com.yoziming.javamall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoziming.javamall.product.entity.CategoryBrandRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分類關聯
 *
 * @author yoziming
 * @date 2021-01-10 22:30:47
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("catId") Long catId, @Param("name") String name);
}
