package yozi.mall.coupon.dao;

import yozi.mall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 優惠券信息
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
