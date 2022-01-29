package yozi.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yozi.mall.common.to.SkuReductionTo;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.coupon.dao.SkuFullReductionDao;
import yozi.mall.coupon.entity.MemberPriceEntity;
import yozi.mall.coupon.entity.SkuFullReductionEntity;
import yozi.mall.coupon.entity.SkuLadderEntity;
import yozi.mall.coupon.service.MemberPriceService;
import yozi.mall.coupon.service.SkuFullReductionService;
import yozi.mall.coupon.service.SkuLadderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    SkuLadderService skuLadderService;

    @Autowired
    MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveInfo(SkuReductionTo skuReductionTo) {
        // 保存階梯優惠
        if (skuReductionTo.getFullCount() > 0) {
            SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
            skuLadderEntity.setSkuId(skuReductionTo.getSkuId());
            skuLadderEntity.setFullCount(skuReductionTo.getFullCount());
            skuLadderEntity.setDiscount(skuReductionTo.getDiscount());
            skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
            skuLadderService.save(skuLadderEntity);
        }

        // 保存滿減優惠
        if (skuReductionTo.getFullPrice().compareTo(BigDecimal.ZERO) > 0) {
            SkuFullReductionEntity fullReductionEntity = new SkuFullReductionEntity();
            fullReductionEntity.setSkuId(skuReductionTo.getSkuId());
            fullReductionEntity.setFullPrice(skuReductionTo.getFullPrice());
            fullReductionEntity.setReducePrice(skuReductionTo.getReducePrice());
            fullReductionEntity.setAddOther(skuReductionTo.getCountStatus());
            this.save(fullReductionEntity);
        }

        // 保存會員優惠
        List<MemberPriceEntity> collect = skuReductionTo.getMemberPrice().stream().map(e -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(skuReductionTo.getSkuId());
            memberPriceEntity.setMemberLevelId(e.getId());
            memberPriceEntity.setMemberLevelName(e.getName());
            memberPriceEntity.setMemberPrice(e.getPrice());
            // 預設可疊加會員優惠
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).filter(e -> e.getMemberPrice().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }

}