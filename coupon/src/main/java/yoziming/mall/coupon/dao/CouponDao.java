package yoziming.mall.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import yoziming.mall.coupon.entity.CouponEntity;

/**
 * 優惠券信息
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {

}
