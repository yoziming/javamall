package yoziming.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yoziming.mall.common.to.SkuReductionTo;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.Query;
import yoziming.mall.coupon.dao.SkuFullReductionDao;
import yoziming.mall.coupon.entity.MemberPriceEntity;
import yoziming.mall.coupon.entity.SkuFullReductionEntity;
import yoziming.mall.coupon.entity.SkuLadderEntity;
import yoziming.mall.coupon.service.MemberPriceService;
import yoziming.mall.coupon.service.SkuFullReductionService;
import yoziming.mall.coupon.service.SkuLadderService;

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