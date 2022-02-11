package com.yoziming.javamall.product.service.impl;

import com.yoziming.javamall.product.vo.SkuItemSaleAttrVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;

import com.yoziming.javamall.product.dao.SkuSaleAttrValueDao;
import com.yoziming.javamall.product.entity.SkuSaleAttrValueEntity;
import com.yoziming.javamall.product.service.SkuSaleAttrValueService;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(Long spuId) {
        List<SkuItemSaleAttrVo> saleAttrVos = this.baseMapper.getSaleAttrsBySpuId(spuId);

        return saleAttrVos;

    }

    @Override
    public List<String> getSkuSaleAttrValuesAsStringList(Long skuId) {
        return baseMapper.getSkuSaleAttrValuesAsStringList(skuId);
    }

}