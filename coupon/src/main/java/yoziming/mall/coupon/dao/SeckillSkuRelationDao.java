package yoziming.mall.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import yoziming.mall.coupon.entity.SeckillSkuRelationEntity;

/**
 * 秒殺活動商品關聯
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:26
 */
@Mapper
public interface SeckillSkuRelationDao extends BaseMapper<SeckillSkuRelationEntity> {

}
