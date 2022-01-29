package yozi.mall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yozi.mall.product.entity.AttrGroupEntity;
import yozi.mall.product.vo.SpuItemAttrGroupVo;

import java.util.List;

/**
 * 屬性分組
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId,
                                                          @Param("catalogId") Long catalogId);
}
