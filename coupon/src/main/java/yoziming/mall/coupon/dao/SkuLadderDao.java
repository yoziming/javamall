package yoziming.mall.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import yoziming.mall.coupon.entity.SkuLadderEntity;

/**
 * 商品階梯價格
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:26
 */
@Mapper
public interface SkuLadderDao extends BaseMapper<SkuLadderEntity> {

}
