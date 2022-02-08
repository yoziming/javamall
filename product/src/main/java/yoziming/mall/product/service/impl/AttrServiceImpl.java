package yoziming.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoziming.mall.common.constant.ProductConstant;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.Query;
import yoziming.mall.product.dao.AttrAttrgroupRelationDao;
import yoziming.mall.product.dao.AttrDao;
import yoziming.mall.product.dao.AttrGroupDao;
import yoziming.mall.product.dao.CategoryDao;
import yoziming.mall.product.entity.AttrAttrgroupRelationEntity;
import yoziming.mall.product.entity.AttrEntity;
import yoziming.mall.product.entity.AttrGroupEntity;
import yoziming.mall.product.entity.CategoryEntity;
import yoziming.mall.product.service.AttrService;
import yoziming.mall.product.service.CategoryService;
import yoziming.mall.product.vo.AttrGroupRelationVo;
import yoziming.mall.product.vo.AttrRespVo;
import yoziming.mall.product.vo.AttrVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao relationDao;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryService categoryService;

    @Override

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        // 先保存基本資料
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);
        // 保存關聯關係，是基本屬性才要保存(銷售不用)
        if (attrEntity.getAttrType() == ProductConstant.ATTR_TYPE_BASE.getCode()
                && attr.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }

    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catalogId, String attrType) {
        QueryWrapper<AttrEntity> qw = new QueryWrapper<>();
        // 判斷是要銷售還是基本屬性，1=基本，0=銷售
        qw.eq("attr_type", "base".equalsIgnoreCase(attrType) ? ProductConstant.ATTR_TYPE_BASE.getCode() :
                ProductConstant.ATTR_TYPE_SALE.getCode());

        // 指定三級分類
        if (catalogId != 0) {
            qw.eq("catalog_id", catalogId);
        }
        // 有查詢條件
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            qw.and((wrapper) -> {
                wrapper.eq("attr_id", key).or().like("attr_name", key);
            });
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params), qw);
        // 到這邊查完Attr基本訊息，把頁數總數那些啥的用PageUtils封裝起來
        PageUtils pageUtils = new PageUtils(page);

        // 但是內容物還沒完，將查到的紀錄一筆筆拿出來，找他們的catalogName與groupName
        List<AttrEntity> attrEntities = page.getRecords();

        // 流式，把一筆筆資料當水流，過完管道就處理完了
        List<AttrRespVo> respVos = attrEntities.stream().map(attrEntity -> {
            // 抓出裡面的attrEntity，拷貝到AttrRespVo上
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            // 是base，非銷售屬性才要找
            if (attrEntity.getAttrType() == ProductConstant.ATTR_TYPE_BASE.getCode()) {
                // 去關聯表拿relationEntity，用來找到AttrGroupId，注意是selectOne
                AttrAttrgroupRelationEntity relationEntity =
                        relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",
                                attrEntity.getAttrId()));
                // 拿到groupName分組名: 主體、螢幕等
                if (relationEntity != null && relationEntity.getAttrGroupId() != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
                // 拿到catalogName分類名(電器-大家電-電視)
                CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatalogId());
                if (categoryEntity != null) {
                    attrRespVo.setCatalogName(categoryEntity.getName());
                }
            }
            return attrRespVo;
        }).collect(Collectors.toList()); // 接起處理完的流，裝到List<AttrRespVo> respVos

        // 把多賦好2個屬性的list塞回去
        pageUtils.setList(respVos);
        return pageUtils;
    }

    /**
     * 屬性詳情(給修改回顯用)
     *
     * @param attrId
     * @return
     */
    @Override
    public AttrRespVo getAttrById(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity, attrRespVo);
        if (attrEntity.getAttrType() == ProductConstant.ATTR_TYPE_BASE.getCode()) {
            // 去關聯表拿relationEntity，用來找到AttrGroupId，注意是selectOne
            AttrAttrgroupRelationEntity relationEntity =
                    relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",
                            attrEntity.getAttrId()));
            // 拿到groupName分組名: 主體、螢幕等
            if (relationEntity != null) {
                attrRespVo.setAttrGroupId(relationEntity.getAttrGroupId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                if (attrGroupEntity != null) {
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }

        // 完整分類路徑，之前寫過的
        Long catalogId = attrEntity.getCatalogId();
        Long[] catalogPath = categoryService.findCatalogPath(catalogId);
        attrRespVo.setCatalogPath(catalogPath);
        // 自己的三級分類名
        CategoryEntity categoryEntity = categoryDao.selectById(catalogId);
        if (categoryEntity != null) {
            attrRespVo.setCatalogName(categoryEntity.getName());
        }

        return attrRespVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        // 先更新基本資料
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);

        if (attrEntity.getAttrType() == ProductConstant.ATTR_TYPE_BASE.getCode()) {

            // 保存關聯關係
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attr.getAttrId());
            // 先判斷本來有無值
            Long count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq(
                    "attr_id",
                    attr.getAttrId()));
            if (count > 0) {
                // 更新
                relationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",
                        attr.getAttrId()));
            } else {
                // 新增
                relationDao.insert(relationEntity);
            }
        }
    }

    @Override
    public List<AttrEntity> getAttrRelation(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> relationEntities =
                relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id",
                        attrgroupId));
        // 收集出attrId
        List<Long> longList =
                relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        if (longList == null || longList.size() == 0) {
            return null;
        }
        return this.listByIds(longList);
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        // 無情將VO再轉回來 哭R
        List<AttrAttrgroupRelationEntity> entities = Arrays.stream(vos).map(item -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        relationDao.batchDelete(entities);
    }

    @Override
    public PageUtils getAttrNoRelation(Map<String, Object> params, Long attrgroupId) {
        // 只查自己所屬分類中的屬性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        // 獲取當前分類的id
        Long catalogId = attrGroupEntity.getCatalogId();

        // 只關連沒引用的屬性，這裡用排除法把已經被關聯的ID找出來，這是用來存排除的清單
        List<Long> alreadyInGroupIdList = new ArrayList<>();
        // 先找出當前分類下的其他分組
        List<AttrGroupEntity> attr_groups = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq(
                "catalog_id"
                , catalogId));
        List<Long> attr_group_ids =
                attr_groups.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());
        // 現在獲取到可能很多個attr_group_id，一個attr_group_id又會對應多個attr_id
        if (attr_group_ids != null && attr_group_ids.size() > 0) {
            // 到pms_attr_attrgroup_relation，拿attr_group_id找出對應的relationEntities
            List<AttrAttrgroupRelationEntity> relationEntities =
                    relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id",
                            attr_group_ids));
            // 這些relationEntities有attr_group_id，表示他的attr_id已經在某個分組內了
            if (relationEntities != null && relationEntities.size() > 0) {
                // 把其中的ID一個一個找出來，這些是要排除的
                alreadyInGroupIdList =
                        relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
            }
        }
        // 可以開查了
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>();
        // 如果有要排除的
        if (alreadyInGroupIdList != null && alreadyInGroupIdList.size() > 0) {
            // 只查自己所屬分類中的屬性，移除掉要排除的，而且只要基本屬性
            queryWrapper.eq("catalog_id", catalogId).notIn("attr_id", alreadyInGroupIdList).eq("attr_type",
                    ProductConstant.ATTR_TYPE_BASE.getCode());
        } else {
            // 只查自己所屬分類中的屬性，而且只要基本屬性
            queryWrapper.eq("catalog_id", catalogId).eq("attr_type", ProductConstant.ATTR_TYPE_BASE.getCode());
        }
        // 前端要的是page，還可能要查key
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.and(qw -> {
                qw.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params),
                queryWrapper);
        PageUtils pageUtils = new PageUtils(page);
        return pageUtils;
    }

    @Override
    public void removeAttrAndRelationByIds(List<Long> asList) {
        this.removeByIds(asList);
        // 還要移除關聯
        List<AttrAttrgroupRelationEntity> entities =
                relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_id", asList));
        if (entities != null && entities.size() > 0) {
            relationDao.batchDelete(entities);
        }
    }

    @Override
    public List<Long> selectSearchAttrs(List<Long> attrIds) {
        return baseMapper.selectSearchAttrs(attrIds);
    }
}



