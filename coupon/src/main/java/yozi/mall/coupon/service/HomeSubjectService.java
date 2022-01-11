package yozi.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.coupon.entity.HomeSubjectEntity;

import java.util.Map;

/**
 * 首頁專題表【jd首頁下面很多專題，每個專題鏈接新的頁面，展示專題商品信息】
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
public interface HomeSubjectService extends IService<HomeSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

