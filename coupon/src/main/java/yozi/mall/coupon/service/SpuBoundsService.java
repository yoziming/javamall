package yozi.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.coupon.entity.SpuBoundsEntity;

import java.util.Map;

/**
 * 商品spu積分設置
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:26
 */
public interface SpuBoundsService extends IService<SpuBoundsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

