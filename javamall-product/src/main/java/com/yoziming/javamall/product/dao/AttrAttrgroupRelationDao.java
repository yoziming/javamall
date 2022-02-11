package com.yoziming.javamall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoziming.javamall.product.entity.AttrAttrgroupRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 屬性&屬性分組關聯
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {
    void deleteBatchRelation(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
}
