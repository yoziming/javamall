package yoziming.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.Query;
import yoziming.mall.product.dao.AttrGroupDao;
import yoziming.mall.product.entity.AttrEntity;
import yoziming.mall.product.entity.AttrGroupEntity;
import yoziming.mall.product.service.AttrGroupService;
import yoziming.mall.product.service.AttrService;
import yoziming.mall.product.vo.AttrGroupWithAttrsVo;
import yoziming.mall.product.vo.SpuItemAttrGroupVo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Autowired
    AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageCatalogId(Map<String, Object> params, Long catalogId) {
        QueryWrapper<AttrGroupEntity> qw = new QueryWrapper<>();
        // select * from pms_attr_group where catalog_id=? and (attr_group_id=key or attr_group_name like %key%)
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            qw.and((obj) -> {
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }
        // 沒指定三級分類
        if (catalogId == 0) {
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params), qw);
            return new PageUtils(page);
        } else {
            qw.eq("catalog_id", catalogId);

            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params), qw);
            return new PageUtils(page);
        }

    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatalogId(Long catalogId) {
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catalog_id",
                catalogId));
        List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(e -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(e, attrGroupWithAttrsVo);
            // 獲取attrs，用之前寫過的方法從分組ID就能找到
            List<AttrEntity> attrEntityList = attrService.getAttrRelation(e.getAttrGroupId());
            // 裝進VO
            attrGroupWithAttrsVo.setAttrs(attrEntityList);
            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {
        //1、查出當前spu對應的所有屬性的分組信息以及當前分組下的所有屬性對應的值
        List<SpuItemAttrGroupVo> vos = baseMapper.getAttrGroupWithAttrsBySpuId(spuId, catalogId);
        return vos;
    }

}