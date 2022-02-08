package yoziming.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.coupon.entity.SeckillPromotionEntity;

import java.util.Map;

/**
 * 秒殺活動
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

