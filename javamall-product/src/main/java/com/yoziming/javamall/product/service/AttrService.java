package com.yoziming.javamall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.product.entity.AttrEntity;
import com.yoziming.javamall.product.vo.AttrGroupRelationVo;
import com.yoziming.javamall.product.vo.AttrRespVo;
import com.yoziming.javamall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品屬性
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catalogId, String attrType);

    AttrRespVo getAttrInfo(Long attrId);

    void saveAttr(AttrVo attr);

    void updateAttrById(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    /**
     * 在指定的所有屬性集合裏面，挑出檢索屬性
     *
     * @param attrIds
     * @return
     */
    List<Long> selectSearchAttrs(List<Long> attrIds);
}

