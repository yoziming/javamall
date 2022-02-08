package yoziming.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.Query;
import yoziming.mall.coupon.dao.SeckillSessionDao;
import yoziming.mall.coupon.entity.SeckillSessionEntity;
import yoziming.mall.coupon.entity.SeckillSkuRelationEntity;
import yoziming.mall.coupon.service.SeckillSessionService;
import yoziming.mall.coupon.service.SeckillSkuRelationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillSessionEntity> page = this.page(
                new Query<SeckillSessionEntity>().getPage(params),
                new QueryWrapper<SeckillSessionEntity>()
        );

        return new PageUtils(page);
    }

    @Autowired
    private SeckillSkuRelationService seckillSkuRelationService;

    @Override
    public List<SeckillSessionEntity> getLates3DaySession() {

        //計算最近三天
        //查出這三天參與秒殺活動的商品
        List<SeckillSessionEntity> list = this.baseMapper.selectList(new QueryWrapper<SeckillSessionEntity>()
                .between("start_time", startTime(), endTime()));

        if (list != null && list.size() > 0) {
            List<SeckillSessionEntity> collect = list.stream().map(session -> {
                Long id = session.getId();
                //查出sms_seckill_sku_relation表中關聯的skuId
                List<SeckillSkuRelationEntity> relationSkus =
                        seckillSkuRelationService.list(new QueryWrapper<SeckillSkuRelationEntity>()
                                .eq("promotion_session_id", id));
                session.setRelationSkus(relationSkus);
                return session;
            }).collect(Collectors.toList());
            return collect;
        }

        return null;
    }

    /**
     * 當前時間
     *
     * @return
     */
    private String startTime() {
        LocalDate now = LocalDate.now();
        LocalTime min = LocalTime.MIN;
        LocalDateTime start = LocalDateTime.of(now, min);

        //格式化時間
        String startFormat = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return startFormat;
    }

    /**
     * 結束時間
     *
     * @return
     */
    private String endTime() {
        LocalDate now = LocalDate.now();
        LocalDate plus = now.plusDays(2);
        LocalTime max = LocalTime.MAX;
        LocalDateTime end = LocalDateTime.of(plus, max);

        //格式化時間
        String endFormat = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return endFormat;
    }
}