package yozi.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品滿減信息
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:26
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

