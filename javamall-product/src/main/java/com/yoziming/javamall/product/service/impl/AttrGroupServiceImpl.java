package com.yoziming.javamall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;
import com.yoziming.javamall.product.dao.AttrGroupDao;
import com.yoziming.javamall.product.entity.AttrEntity;
import com.yoziming.javamall.product.entity.AttrGroupEntity;
import com.yoziming.javamall.product.entity.CategoryEntity;
import com.yoziming.javamall.product.service.AttrGroupService;
import com.yoziming.javamall.product.service.AttrService;
import com.yoziming.javamall.product.service.CategoryService;
import com.yoziming.javamall.product.vo.AttrGroupWithAttrsVo;
import com.yoziming.javamall.product.vo.SpuItemAttrGroupVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    AttrService attrService;
    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        //select * from pms_attr_group where catelog_id=? and (attr_group_id=key or attr_group_name like %key%)
        String key = (String) params.get("key");

        //構造QueryWrapper
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>();

        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) -> {
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }

        //如果傳過來的三級分類id為0，就查詢所有數據
        if (catelogId == 0) {
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            page.setRecords(
                    page.getRecords().stream().map(
                            (item) -> {
                                CategoryEntity categoryEntity = categoryService.getById(item.getCatelogId());
                                item.setCategoryName(categoryEntity.getName());
                                return item;
                            }
                    ).collect(Collectors.toList())
            );
            return new PageUtils(page);
        } else {
            wrapper.eq("catelog_id", catelogId);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            page.setRecords(
                    page.getRecords().stream().map(
                            (item) -> {
                                CategoryEntity categoryEntity = categoryService.getById(item.getCatelogId());
                                item.setCategoryName(categoryEntity.getName());
                                return item;
                            }
                    ).collect(Collectors.toList())
            );
            return new PageUtils(page);
        }
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {

        //1、查詢分組訊息
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id",
                catelogId));

        //2、查詢所有屬性
        List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(group -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group, attrGroupWithAttrsVo);

            List<AttrEntity> attrs = attrService.getRelationAttr(attrGroupWithAttrsVo.getAttrGroupId());
            attrGroupWithAttrsVo.setAttrs(attrs);

            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {
        //1、查出當前spu對應的所有屬性的分組訊息以及當前分組下的所有屬性對應的值
        AttrGroupDao baseMapper = this.getBaseMapper();
        List<SpuItemAttrGroupVo> vos = baseMapper.getAttrGroupWithAttrsBySpuId(spuId, catalogId);

        //SELECT pav.`spu_id`,ag.`attr_group_name`,ag.`attr_group_id`,aar.`attr_id`,attr.`attr_name`,pav.`attr_value`
        // FROM `pms_attr_group` ag
        // LEFT JOIN `pms_attr_attrgroup_relation` aar
        // ON aar.`attr_group_id` = ag.`attr_group_id`
        // LEFT JOIN `pms_attr` attr ON attr.`attr_id` = aar.`attr_id`
        // LEFT JOIN `pms_product_attr_value` pav ON pav.`attr_id` = attr.`attr_id`
        // WHERE ag.catelog_id = 225 AND pav.`spu_id` = 13
        return vos;
    }

}