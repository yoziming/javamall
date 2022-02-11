package com.yoziming.javamall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;
import com.yoziming.javamall.order.dao.PaymentInfoDao;
import com.yoziming.javamall.order.entity.PaymentInfoEntity;
import com.yoziming.javamall.order.service.PaymentInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("paymentInfoService")
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoDao, PaymentInfoEntity> implements PaymentInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PaymentInfoEntity> wrapper = new QueryWrapper<PaymentInfoEntity>();
        wrapper.orderByDesc("create_time");
        if (!StringUtils.isEmpty(params.get("orderSn"))){
            wrapper.eq("order_sn", params.get("orderSn"));
        }
        IPage<PaymentInfoEntity> page = this.page(
                new Query<PaymentInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}