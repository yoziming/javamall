package yozi.mall.coupon.dao;

import yozi.mall.coupon.entity.SkuFullReductionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品滿減信息
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:26
 */
@Mapper
public interface SkuFullReductionDao extends BaseMapper<SkuFullReductionEntity> {
	
}
