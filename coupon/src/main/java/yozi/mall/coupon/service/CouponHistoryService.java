package yozi.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.coupon.entity.CouponHistoryEntity;

import java.util.Map;

/**
 * 優惠券領取歷史記錄
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
public interface CouponHistoryService extends IService<CouponHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

