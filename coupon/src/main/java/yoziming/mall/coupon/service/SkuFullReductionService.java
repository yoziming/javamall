package yoziming.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.to.SkuReductionTo;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品滿減信息
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:26
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveInfo(SkuReductionTo skuReductionTo);
}

