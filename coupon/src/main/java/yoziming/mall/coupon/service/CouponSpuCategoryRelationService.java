package yoziming.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.coupon.entity.CouponSpuCategoryRelationEntity;

import java.util.Map;

/**
 * 優惠券分類關聯
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
public interface CouponSpuCategoryRelationService extends IService<CouponSpuCategoryRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

