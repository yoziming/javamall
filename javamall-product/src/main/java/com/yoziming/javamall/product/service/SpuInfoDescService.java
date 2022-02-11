package com.yoziming.javamall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spu訊息介紹
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfoDesc(SpuInfoDescEntity spuInfoDescEntity);
}

