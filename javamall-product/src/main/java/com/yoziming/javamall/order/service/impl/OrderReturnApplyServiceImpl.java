package com.yoziming.javamall.order.service.impl;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;
import com.yoziming.common.vo.MemberRespVo;
import com.yoziming.javamall.config.AlipayTemplate;
import com.yoziming.javamall.interceptor.LoginUserInterceptor;
import com.yoziming.javamall.order.dao.OrderReturnApplyDao;
import com.yoziming.javamall.order.entity.OrderEntity;
import com.yoziming.javamall.order.entity.OrderReturnApplyEntity;
import com.yoziming.javamall.order.entity.RefundInfoEntity;
import com.yoziming.javamall.order.enums.OrderStatusEnum;
import com.yoziming.javamall.order.service.OrderReturnApplyService;
import com.yoziming.javamall.order.service.OrderService;
import com.yoziming.javamall.order.service.RefundInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service("orderReturnApplyService")
public class OrderReturnApplyServiceImpl extends ServiceImpl<OrderReturnApplyDao, OrderReturnApplyEntity> implements OrderReturnApplyService {

    @Autowired
    OrderService orderService;

    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    RefundInfoService refundInfoService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<OrderReturnApplyEntity> wrapper = new QueryWrapper<OrderReturnApplyEntity>().orderByDesc(
                "create_time");
        if (!StringUtils.isEmpty(params.get("orderSn"))) {
            wrapper.eq("order_sn", params.get("orderSn"));
        }
        if (!StringUtils.isEmpty(params.get("memberUsername"))) {
            wrapper.eq("member_username", params.get("memberUsername"));
        }
        if (!StringUtils.isEmpty(params.get("status"))) {
            wrapper.eq("status", params.get("status"));
        }
        IPage<OrderReturnApplyEntity> page = this.page(
                new Query<OrderReturnApplyEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void returnProduct(String orderSn, String reason, String returnAmount) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("order_sn", orderSn);
        wrapper.eq("member_id", memberRespVo.getId());
        OrderEntity orderEntity = orderService.getOne(wrapper);

        if (new BigDecimal(returnAmount).compareTo(orderEntity.getPayAmount()) > 0) {
            // 申請的金額大於總付款金額
            return;
        }

        // 修改訂單狀態
        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setOrderSn(orderSn);
        orderEntity1.setStatus(OrderStatusEnum.SERVICING.getCode());

        orderService.update(orderEntity1, wrapper);

        // 添加售後訂單
        OrderReturnApplyEntity returnApplyEntityBuilder = OrderReturnApplyEntity.builder()
                .createTime(new Date())
                .orderId(orderEntity.getId())
                .orderSn(orderSn)
                .reason(reason)
                .returnAmount(new BigDecimal(returnAmount))
                .status(0)
                .memberUsername(memberRespVo.getUsername())
                .build();
        this.save(returnApplyEntityBuilder);

    }

    @Override
    @Transactional
    public void updateReturn(OrderReturnApplyEntity orderReturnApply) {
        if (orderReturnApply.getStatus() == 2) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setStatus(OrderStatusEnum.SERVICED.getCode());
            orderEntity.setId(orderReturnApply.getOrderId());
            orderService.updateOrder(orderEntity);

            // 退款

            AlipayTradeRefundResponse response = alipayTemplate.refund(orderReturnApply.getOrderSn(),
                    orderReturnApply.getReturnAmount());
            log.info("支付寶退款：ordersn={}", orderReturnApply.getOrderSn());
            RefundInfoEntity refundInfo = new RefundInfoEntity();
            if (response.isSuccess()) {
                // 保存退款訊息
                refundInfo.setRefundSn(response.getTradeNo());
                refundInfo.setRefund(new BigDecimal(response.getRefundFee()));
                refundInfo.setOrderReturnId(response.getOutTradeNo());
                refundInfo.setRefundStatus(1);
                refundInfoService.save(refundInfo);
            } else {
                refundInfo.setRefundSn(response.getTradeNo());
                refundInfo.setRefund(new BigDecimal(response.getRefundFee()));
                refundInfo.setOrderReturnId(response.getOutTradeNo());
                refundInfo.setRefundStatus(0);
                refundInfoService.save(refundInfo);
                throw new RuntimeException("退款失敗");
            }
        }
        this.updateById(orderReturnApply);
    }

}