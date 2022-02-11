package com.yoziming.javamall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.product.entity.SpuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * spu圖片
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveImages(Long id, List<String> images);
}

