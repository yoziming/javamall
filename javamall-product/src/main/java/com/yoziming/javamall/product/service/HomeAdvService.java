package com.yoziming.javamall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.product.entity.HomeAdvEntity;

import java.util.List;
import java.util.Map;

/**
 * 首頁輪播廣告
 *
 * @author yoziming
 * @date 2022-01-01 17:25:51
 */
public interface HomeAdvService extends IService<HomeAdvEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<HomeAdvEntity> getHomeAdv();

    List<List<HomeAdvEntity>> getIndexAdv();
}

