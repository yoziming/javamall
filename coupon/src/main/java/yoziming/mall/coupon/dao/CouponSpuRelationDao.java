package yoziming.mall.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import yoziming.mall.coupon.entity.CouponSpuRelationEntity;

/**
 * 優惠券與產品關聯
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
@Mapper
public interface CouponSpuRelationDao extends BaseMapper<CouponSpuRelationEntity> {

}
