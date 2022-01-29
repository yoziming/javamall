package yozi.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.product.entity.AttrEntity;
import yozi.mall.product.vo.AttrGroupRelationVo;
import yozi.mall.product.vo.AttrRespVo;
import yozi.mall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品屬性
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catalogId, String attrType);

    AttrRespVo getAttrById(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getAttrRelation(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getAttrNoRelation(Map<String, Object> params, Long attrgroupId);

    void removeAttrAndRelationByIds(List<Long> asList);

    List<Long> selectSearchAttrs(List<Long> attrIds);

    // PageUtils queryPageCatalogId(Map<String, Object> params, Long catalogId);
}

