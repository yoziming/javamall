package yoziming.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.Query;
import yoziming.mall.product.dao.AttrAttrgroupRelationDao;
import yoziming.mall.product.entity.AttrAttrgroupRelationEntity;
import yoziming.mall.product.service.AttrAttrgroupRelationService;
import yoziming.mall.product.vo.AttrGroupRelationVo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao,
        AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void addAttrGroupRelation(List<AttrGroupRelationVo> vos) {
        List<AttrAttrgroupRelationEntity> collect = vos.stream().map(e -> {
            // 一樣，轉存
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(e, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        // 調用原生批量保存
        this.saveBatch(collect);
    }

}