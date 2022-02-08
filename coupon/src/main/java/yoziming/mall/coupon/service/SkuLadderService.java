package yoziming.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 商品階梯價格
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:26
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

