package yozi.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.coupon.entity.SeckillSkuRelationEntity;

import java.util.Map;

/**
 * 秒殺活動商品關聯
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:26
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

