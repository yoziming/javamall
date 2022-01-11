package yozi.mall.product.dao;

import yozi.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三級分類
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
