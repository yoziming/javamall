package com.yoziming.javamall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.product.entity.SkuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * sku圖片
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SkuImagesEntity> getImagesBySkuId(Long skuId);
}

