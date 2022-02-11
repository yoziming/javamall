package com.yoziming.javamall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoziming.javamall.product.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品三級分類
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

    List<CategoryEntity> selectCategoryByIds(@Param("catId") Long catId);

}
