package yozi.mall.product.dao;

import yozi.mall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品屬性
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {
	
}
