package com.yoziming.javamall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.product.entity.AttrAttrgroupRelationEntity;
import com.yoziming.javamall.product.vo.AttrGroupRelationVo;

import java.util.List;
import java.util.Map;

/**
 * 屬性&屬性分組關聯
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBatch(List<AttrGroupRelationVo> vos);
}

