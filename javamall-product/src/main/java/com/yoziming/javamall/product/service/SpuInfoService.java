package com.yoziming.javamall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.product.entity.SpuInfoEntity;
import com.yoziming.javamall.product.vo.SpuSaveVo;
import com.yoziming.javamall.product.vo.SpuUpdateVo;

import java.util.Map;

/**
 * spu訊息
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    void savesupInfo(SpuSaveVo vo);

    PageUtils queryPageByCondtion(Map<String, Object> params);

    void up(Long spuId);

    SpuInfoEntity getSpuInfoBySkuId(Long skuId);

    void down(Long spuId);

    SpuUpdateVo getSpu(Long id);

    void removeSpu(Long id);

    boolean isPublish(Long id);
}

