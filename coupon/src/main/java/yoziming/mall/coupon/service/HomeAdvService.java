package yoziming.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.coupon.entity.HomeAdvEntity;

import java.util.Map;

/**
 * 首頁輪播廣告
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
public interface HomeAdvService extends IService<HomeAdvEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

