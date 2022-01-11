package yozi.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.coupon.entity.MemberPriceEntity;

import java.util.Map;

/**
 * 商品會員價格
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
public interface MemberPriceService extends IService<MemberPriceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

