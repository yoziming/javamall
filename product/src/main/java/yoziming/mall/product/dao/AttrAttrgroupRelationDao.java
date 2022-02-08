package yoziming.mall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yoziming.mall.product.entity.AttrAttrgroupRelationEntity;

import java.util.List;

/**
 * 屬性&屬性分組關聯
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    void batchDelete(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
}
