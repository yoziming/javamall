package yozi.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.product.dao.ProductAttrValueDao;
import yozi.mall.product.entity.ProductAttrValueEntity;
import yozi.mall.product.service.ProductAttrValueService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    // 獲取spu規格
    @Override
    public List<ProductAttrValueEntity> baseAttrListforspu(Long spuId) {
        List<ProductAttrValueEntity> attrValueEntityList = this.baseMapper.selectList(
                new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
        return attrValueEntityList;
    }

    /**
     * 修改商品規格
     *
     * @param spuId
     * @param entities
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> entities) {
        // 1、刪除spuId之前對應的所有屬性
        this.baseMapper.delete(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));

        // 2、添加商品規格信息
        List<ProductAttrValueEntity> collect = entities.stream().map(item -> {
            item.setSpuId(spuId);
            return item;
        }).collect(Collectors.toList());

        // 批量新增
        this.saveBatch(collect);
    }

}