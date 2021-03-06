package com.yoziming.javamall.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.to.mq.OrderTo;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;
import com.yoziming.common.utils.R;
import com.yoziming.common.vo.MemberRespVo;
import com.yoziming.javamall.cart.service.CartService;
import com.yoziming.javamall.cart.vo.CartItem;
import com.yoziming.javamall.config.AlipayTemplate;
import com.yoziming.javamall.interceptor.LoginUserInterceptor;
import com.yoziming.javamall.member.controller.MemberReceiveAddressController;
import com.yoziming.javamall.member.entity.MemberReceiveAddressEntity;
import com.yoziming.javamall.member.service.MemberReceiveAddressService;
import com.yoziming.javamall.order.constant.OrderConstant;
import com.yoziming.javamall.order.dao.OrderDao;
import com.yoziming.javamall.order.dao.OrderItemDao;
import com.yoziming.javamall.order.entity.OrderEntity;
import com.yoziming.javamall.order.entity.OrderItemEntity;
import com.yoziming.javamall.order.entity.PaymentInfoEntity;
import com.yoziming.javamall.order.entity.RefundInfoEntity;
import com.yoziming.javamall.order.enums.OrderStatusEnum;
import com.yoziming.javamall.order.service.*;
import com.yoziming.javamall.order.to.OrderCreateTo;
import com.yoziming.javamall.order.vo.*;
import com.yoziming.javamall.product.controller.SpuInfoController;
import com.yoziming.javamall.product.service.SkuSaleAttrValueService;
import com.yoziming.javamall.ware.controller.WareSkuController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    private ThreadLocal<OrderSubmitVo> submitVoThreadLocal = new ThreadLocal<>();

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    MemberReceiveAddressController memberReceiveAddressController;

    @Autowired
    MemberReceiveAddressService memberReceiveAddressService;

    @Autowired
    WareSkuController wareSkuController;

    @Autowired
    CartService cartService;
    @Autowired
    ThreadPoolExecutor executor;

    @Autowired
    SpuInfoController spuInfoController;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    PaymentInfoService paymentInfoService;

    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    RefundInfoService refundInfoService;

    @Autowired
    OrderReturnApplyService orderReturnApplyService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<OrderEntity>().orderByDesc("create_time");
        if (!StringUtils.isEmpty(params.get("orderSn"))) {
            wrapper.eq("order_sn", params.get("orderSn"));
        }
        if (!StringUtils.isEmpty(params.get("memberUsername"))) {
            wrapper.eq("member_username", params.get("memberUsername"));
        }
        if (!StringUtils.isEmpty(params.get("status"))) {
            wrapper.eq("status", params.get("status"));
        }
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * ???????????????????????????????????????
     *
     * @return
     */
    @Override
    public OrderConfirmVo confirmOrder() {
        try {
            OrderConfirmVo confirmVo = new OrderConfirmVo();
            MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
            log.info("service_member={}", memberRespVo);

            CompletableFuture<Void> getAddressFuture = CompletableFuture.runAsync(() -> {
                //1?????????????????????????????????
                //???????????????????????????
                List<MemberReceiveAddressEntity> addressEntities =
                        memberReceiveAddressController.getAddress(memberRespVo.getId());
                confirmVo.setAddress(addressEntities);
            }, executor);

            CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
                //2??????????????????????????????????????????
                //???????????????????????????

                List<CartItem> cartItems = cartService.getUserCartItems(memberRespVo);

                confirmVo.setItems(cartItems);
                //feign???????????????????????????????????????????????????????????????
            }, executor).thenRunAsync(() -> {
                List<CartItem> items = confirmVo.getItems();
                List<Long> collect = items.stream().map(item -> item.getSkuId()).collect(Collectors.toList());

                R hasStock = wareSkuController.getSkuHasStock(collect);
                List<SkuStockVo> data = hasStock.getData(new TypeReference<List<SkuStockVo>>() {
                });
                if (data != null) {
                    Map<Long, Boolean> map = data.stream().collect(Collectors.toMap(SkuStockVo::getSkuId,
                            SkuStockVo::getHasStock));
                    confirmVo.setStocks(map);
                }
            }, executor);

            //3?????????????????????
            Integer integration = memberRespVo.getIntegration();
            confirmVo.setIntegration(integration == null ? 0 : integration);

            //????????????
            String token = UUID.randomUUID().toString().replace("-", "");
            // ???????????????redis
            redisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberRespVo.getId(), token, 30,
                    TimeUnit.MINUTES);
            confirmVo.setOrderToken(token);

            CompletableFuture.allOf(getAddressFuture, cartFuture).get();

            return confirmVo;
        } catch (Exception e) {
            log.error("??????????????????????????????", e);
            return null;
        }
    }

    @Transactional
    @Override
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo vo) {
        submitVoThreadLocal.set(vo);

        SubmitOrderResponseVo response = new SubmitOrderResponseVo();
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        response.setCode(0);

        //1??????????????????????????????????????????????????????????????????
        //0???????????????1????????????
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 " +
                "end";
        String orderToken = vo.getOrderToken();

        //?????????????????????????????????
        Long result = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                Arrays.asList(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberRespVo.getId()), orderToken);
        if (result == 0L) {
            //??????
            response.setCode(1);
            return response;
        } else {
            //??????????????????
            //1????????????????????????????????????
            OrderCreateTo order = createOrder(memberRespVo);
            //2?????????
            BigDecimal payAmount = order.getOrder().getPayAmount();
            BigDecimal payPrice = vo.getPayPrice();
            if (Math.abs(payAmount.subtract(payPrice).doubleValue()) < 0.01) {
                //????????????
                //3???????????????
                saveOrder(order);
                //4???????????????,????????????????????????????????????
                // ??????????????????????????????skuId,skuName,num???
                WareSkuLockVo lockVo = new WareSkuLockVo();
                lockVo.setOrderSn(order.getOrder().getOrderSn());
                List<OrderItemVo> locks = order.getOrderItems().stream().map(item -> {
                    OrderItemVo itemVo = new OrderItemVo();
                    itemVo.setSkuId(item.getSkuId());
                    itemVo.setCount(item.getSkuQuantity());
                    itemVo.setTitle(item.getSkuName());
                    return itemVo;
                }).collect(Collectors.toList());
                lockVo.setLocks(locks);
                com.yoziming.javamall.ware.vo.WareSkuLockVo lockVo1 = new com.yoziming.javamall.ware.vo.WareSkuLockVo();
                BeanUtils.copyProperties(lockVo, lockVo1);

                //???????????????????????????????????????????????????????????????????????????????????????
                //????????????????????????????????????????????????????????????????????????????????????
                //????????????????????????????????????????????? ??????????????????
                R r = wareSkuController.orderLockStock(lockVo1);
                if (r.getCode() == 0) {
                    //????????????
                    response.setOrder(order.getOrder());
                    rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", order.getOrder());
                    return response;
                } else {
                    //????????????
                    log.error("?????????????????????OrderSubmitVo={}", vo);
                    response.setCode(3);
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return response;
                }
            } else {
                response.setCode(2);
                return response;
            }
        }
    }

    @Override
    public OrderEntity getOrderByOrderSn(String orderSn) {
        OrderEntity order_sn = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));

        return order_sn;
    }

    @Override
    public PageUtils queryPageWithItem(Map<String, Object> params) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>().eq("member_id", memberRespVo.getId()).orderByDesc("id")
        );
        List<OrderEntity> order_sn = page.getRecords().stream().map(order -> {
            List<OrderItemEntity> itemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq(
                    "order_sn", order.getOrderSn()));
            order.setItemEntities(itemEntities);
            return order;
        }).collect(Collectors.toList());
        page.setRecords(order_sn);

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageWithItem2(Integer pageNum) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();

        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<OrderEntity>()
                .eq("member_id", memberRespVo.getId()).orderByDesc("id");

        Page<OrderEntity> page = baseMapper.selectPage(new Page<>(pageNum, 10), wrapper);
        List<OrderEntity> orderEntities = page.getRecords().stream().map(order -> {
            List<OrderItemEntity> itemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq(
                    "order_sn", order.getOrderSn()));
            order.setItemEntities(itemEntities);
            return order;
        }).collect(Collectors.toList());
        page.setRecords(orderEntities);
        return new PageUtils(page);
    }

    /**
     * ???????????????????????????
     *
     * @param orderSn
     */
    @Override
    public OrderEntity getOrderDetail(String orderSn) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("order_sn", orderSn);
        wrapper.eq("member_id", memberRespVo.getId());
        OrderEntity orderEntity = this.getOne(wrapper);
        List<OrderItemEntity> orderItemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq(
                "order_sn", orderEntity.getOrderSn()));
        orderEntity.setItemEntities(orderItemEntities);
        return orderEntity;
    }

    @Override
    public void updateOrder(OrderEntity order) {

        if (order.getStatus() == OrderStatusEnum.SENDED.getCode() && StringUtils.isEmpty(order.getDeliveryTime())) {
            //???????????????
            order.setDeliveryTime(new Date());
            if (StringUtils.isEmpty(order.getDeliveryCompany())) {
                order.setDeliveryCompany("????????????");
            }
        }
        if (order.getStatus() == OrderStatusEnum.SERVICED.getCode()) {
            // ???????????????????????????
            // ????????????
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(order, orderTo);
            try {
                rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other", order);
            } catch (AmqpException e) {
                log.error("??????????????????????????????????????????????????????", e);
                rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other", order);
            }
        }
        order.setModifyTime(new Date());
        this.updateById(order);

    }

    @Override
    public void forcePaySuccess(String orderSn) {

        //1?????????????????????
        PaymentInfoEntity infoEntity = new PaymentInfoEntity();
        infoEntity.setOrderSn(orderSn);
        infoEntity.setPaymentStatus("TRADE_SUCCESS");
        infoEntity.setCallbackTime(new Date());

        paymentInfoService.save(infoEntity);

        //2??????????????????????????????
        //?????????????????????????????????????????????
        this.baseMapper.updateOrderStatus(orderSn, OrderStatusEnum.PAYED.getCode());

    }

    @Override
    public void closeOrder(OrderEntity entity) {
        // ??????????????????
        String payResult = handlePayResult(entity.getOrderSn());
        if (payResult.equals("success")) {
            log.info("???????????????????????????????????????");
            return;
        }
        //???????????????????????????????????????
        OrderEntity orderEntity = this.getById(entity.getId());
        if (orderEntity.getStatus() == OrderStatusEnum.CREATE_NEW.getCode()) {
            //??????
            OrderEntity update = new OrderEntity();
            update.setId(entity.getId());
            update.setStatus(OrderStatusEnum.CANCEL.getCode());
            this.updateById(update);
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(orderEntity, orderTo);
            //??????MQ??????
            try {
                //?????????????????????
                log.info("?????????????????????????????????orderTo={}", orderTo);
                rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other.unlock", orderTo);
            } catch (Exception e) {
                log.error("???????????????????????????????????????", e);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelOrder(String orderSn) {
        //???????????????????????????????????????
        OrderEntity orderEntity = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
        if (orderEntity.getStatus() == OrderStatusEnum.PAYED.getCode()) {
            // ?????????????????????
            OrderEntity update = new OrderEntity();
            update.setId(orderEntity.getId());
            update.setStatus(OrderStatusEnum.CANCEL.getCode());
            this.updateById(update);
            OrderTo orderTo = new OrderTo();
            orderTo.setOrderSn(orderSn);
            log.info("?????????????????????????????????orderTo={}", orderTo);
            rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other.unlock", orderTo);
            // ??????
            RefundInfoEntity refundInfo = new RefundInfoEntity();
            // AlipayTradeRefundResponse response = alipayTemplate.refund(orderSn, orderEntity.getPayAmount());
            // if (response.isSuccess()) {
            //     // ??????????????????
            //     refundInfo.setRefundSn(response.getTradeNo());
            //     refundInfo.setRefund(new BigDecimal(response.getRefundFee()));
            //     refundInfo.setOrderReturnId(response.getOutTradeNo());
            //     refundInfo.setRefundStatus(1);
            //     refundInfoService.save(refundInfo);
            // } else {
            //     refundInfo.setRefundSn(response.getTradeNo());
            //     refundInfo.setRefund(new BigDecimal(response.getRefundFee()));
            //     refundInfo.setOrderReturnId(response.getOutTradeNo());
            //     refundInfo.setRefundStatus(0);
            //     refundInfoService.save(refundInfo);
            //     throw new RuntimeException("????????????");
            // }
        } else if (orderEntity.getStatus() == OrderStatusEnum.CREATE_NEW.getCode()) {
            // ?????????
            //??????
            closeOrder(orderEntity);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirmReceipt(String orderSn) {
        //???????????????????????????????????????
        OrderEntity orderEntity = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
        if (orderEntity.getStatus() == OrderStatusEnum.SENDED.getCode()) {
            //????????????
            OrderEntity update = new OrderEntity();
            update.setId(orderEntity.getId());
            update.setStatus(OrderStatusEnum.RECEIVED.getCode());
            update.setConfirmStatus(1);
            this.updateById(update);
            // ????????????
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(orderEntity, orderTo);
            try {
                // ?????????????????? ?????? ??? ????????????
                //                wmsFeignService.minusWare(orderEntity);
                log.info("????????????????????????");
                rabbitTemplate.convertAndSend("stock-event-exchange", "stock.minus", orderTo);
            } catch (Exception e) {
                log.error("??????????????????????????????", e);
            }
        }
    }

    @Override
    public PayVo getOrderPay(String orderSn) {
        PayVo payVo = new PayVo();
        OrderEntity order = this.getOrderByOrderSn(orderSn);

        BigDecimal bigDecimal = order.getPayAmount().setScale(2, BigDecimal.ROUND_UP);
        payVo.setTotal_amount(bigDecimal.toString());
        payVo.setOut_trade_no(order.getOrderSn());

        List<OrderItemEntity> order_sn = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq("order_sn",
                orderSn));
        OrderItemEntity entity = order_sn.get(0);
        payVo.setSubject(entity.getSkuName());
        payVo.setBody(entity.getSkuAttrsVals());
        return payVo;
    }

    /**
     * ??????????????????
     *
     * @param vo
     * @return
     */
    @Override
    public String handlePayResult(PayAsyncVo vo) {
        //1?????????????????????
        PaymentInfoEntity infoEntity = new PaymentInfoEntity();
        infoEntity.setAlipayTradeNo(vo.getTrade_no());
        infoEntity.setOrderSn(vo.getOut_trade_no());
        infoEntity.setPaymentStatus(vo.getTrade_status());
        infoEntity.setCallbackTime(vo.getNotify_time());

        paymentInfoService.save(infoEntity);

        //2??????????????????????????????
        if (vo.getTrade_status().equals("TRADE_SUCCESS") || vo.getTrade_status().equals("TRADE_FINISHED")) {
            //??????????????????
            String outTradeNo = vo.getOut_trade_no();
            this.baseMapper.updateOrderStatus(outTradeNo, OrderStatusEnum.PAYED.getCode());
        }
        return "success";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String handlePayResult(String orderSn) {

        // AlipayTradeQueryResponse response = alipayTemplate.queryPayResult(orderSn);
        // if (!ObjectUtils.isEmpty(response)) {
        //     if (response.isSuccess()) {
        //         log.info("???????????????????????????,PayAmount={}, TradeStatus={}, OutTradeNo={}, BuyerLogonId={}, BuyerPayAmount={}",
        //                 response.getPayAmount(), response.getTradeStatus(), response.getOutTradeNo(),
        //                 response.getBuyerLogonId(), response.getBuyerPayAmount());
        //         String tradeStatus = response.getTradeStatus();
        //
        //         //1?????????????????????
        //         PaymentInfoEntity infoEntity = new PaymentInfoEntity();
        //         infoEntity.setAlipayTradeNo(response.getTradeNo());
        //         infoEntity.setOrderSn(response.getOutTradeNo());
        //         infoEntity.setPaymentStatus(response.getTradeStatus());
        //         infoEntity.setCallbackTime(response.getSendPayDate());
        //         infoEntity.setCreateTime(new Date());
        //         infoEntity.setSubject(response.getSubject());
        //         infoEntity.setTotalAmount(new BigDecimal(response.getBuyerPayAmount()));
        //         // ??????????????????
        //         //                infoEntity.setCallbackContent(JSONObject.toJSONString(response));
        //
        //         paymentInfoService.save(infoEntity);
        //         // ????????????
        //         if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
        //             //??????????????????
        //             String outTradeNo = response.getOutTradeNo();
        //             this.baseMapper.updateOrderStatus(outTradeNo, OrderStatusEnum.PAYED.getCode());
        //             return "success";
        //         } else {
        //             return "fail";
        //         }
        //     } else {
        //         return "error";
        //     }
        // } else {
        //     return "error";
        // }
        return "success";
    }

    /**
     * ??????????????????
     *
     * @param order
     */
    private void saveOrder(OrderCreateTo order) {
        OrderEntity orderEntity = order.getOrder();
        this.save(orderEntity);

        List<OrderItemEntity> orderItems = order.getOrderItems();
        //        orderItemService.saveBatch(orderItems);
        //?????????????????????????????????seata??????????????????????????????
        // Error updating database.  Cause: io.seata.common.exception.NotSupportYetException
        //???????????????????????????????????????seata?????????????????????
        for (OrderItemEntity orderItem : orderItems) {
            orderItemDao.insert(orderItem);
        }
    }

    private OrderCreateTo createOrder(MemberRespVo memberRespVo) {
        OrderCreateTo orderCreateTo = new OrderCreateTo();
        //1??????????????????
        String orderSn = IdWorker.getTimeId();
        OrderEntity orderEntity = buildOrder(orderSn);

        //2??????????????????????????????
        List<OrderItemEntity> itemEntities = buildOrderItems(orderSn, memberRespVo);

        //3?????????,??????????????????
        computePrice(orderEntity, itemEntities);
        orderCreateTo.setOrder(orderEntity);
        orderCreateTo.setOrderItems(itemEntities);
        return orderCreateTo;
    }

    private void computePrice(OrderEntity orderEntity, List<OrderItemEntity> itemEntities) {
        BigDecimal total = new BigDecimal("0.0");
        //??????????????????????????????????????????????????????
        BigDecimal coupon = new BigDecimal("0.0");
        BigDecimal integration = new BigDecimal("0.0");
        BigDecimal promotion = new BigDecimal("0.0");
        BigDecimal gift = new BigDecimal("0.0");
        BigDecimal growth = new BigDecimal("0.0");
        for (OrderItemEntity entity : itemEntities) {
            coupon = coupon.add(entity.getCouponAmount());
            integration = integration.add(entity.getIntegrationAmount());
            promotion = promotion.add(entity.getPromotionAmount());
            total = total.add(entity.getRealAmount());
            gift = gift.add(new BigDecimal(entity.getGiftIntegration().toString()));
            growth = growth.add(new BigDecimal(entity.getGiftGrowth().toString()));

        }
        //1?????????????????????
        orderEntity.setTotalAmount(total);
        //????????????
        orderEntity.setPayAmount(total.add(orderEntity.getFreightAmount()));
        orderEntity.setPromotionAmount(promotion);
        orderEntity.setIntegrationAmount(integration);
        orderEntity.setCouponAmount(coupon);

        //?????????????????????
        orderEntity.setIntegration(gift.intValue());
        orderEntity.setGrowth(growth.intValue());
        orderEntity.setDeleteStatus(0); //0:?????????
    }

    private OrderEntity buildOrder(String orderSn) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        OrderEntity entity = new OrderEntity();
        entity.setOrderSn(orderSn);
        entity.setMemberId(memberRespVo.getId());
        entity.setMemberUsername(memberRespVo.getUsername());
        //????????????????????????
        OrderSubmitVo orderSubmitVo = submitVoThreadLocal.get();
        MemberReceiveAddressEntity memberReceiveAddress =
                memberReceiveAddressService.getById(orderSubmitVo.getAddrId());
        //??????????????????
        entity.setFreightAmount(BigDecimal.ZERO);
        //?????????????????????
        entity.setReceiverCity(memberReceiveAddress.getCity());
        entity.setReceiverDetailAddress(memberReceiveAddress.getDetailAddress());
        entity.setReceiverName(memberReceiveAddress.getName());
        entity.setReceiverPhone(memberReceiveAddress.getPhone());
        entity.setReceiverProvince(memberReceiveAddress.getProvince());
        entity.setReceiverRegion(memberReceiveAddress.getRegion());
        entity.setReceiverPostCode(memberReceiveAddress.getPostCode());

        //???????????????????????????
        entity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        entity.setAutoConfirmDay(7);
        return entity;
    }

    /**
     * ???????????????????????????
     *
     * @return
     */
    private List<OrderItemEntity> buildOrderItems(String orderSn, MemberRespVo memberRespVo) {
        //????????????????????????????????????
        List<CartItem> cartItems = cartService.getUserCartItems(memberRespVo);

        if (cartItems != null && cartItems.size() > 0) {
            List<OrderItemEntity> itemEntities = cartItems.stream().map(cartItem -> {
                OrderItemEntity itemEntity = buildOrderItem(cartItem);
                itemEntity.setOrderSn(orderSn);
                return itemEntity;
            }).collect(Collectors.toList());
            return itemEntities;
        }
        return null;
    }

    /**
     * ?????????????????????
     *
     * @param cartItem
     * @return
     */
    private OrderItemEntity buildOrderItem(CartItem cartItem) {
        OrderItemEntity itemEntity = new OrderItemEntity();
        //1???????????????????????????
        //2????????????SPU??????
        Long skuId = cartItem.getSkuId();
        R r = spuInfoController.getSpuInfoBySkuId(skuId);
        SpuInfoVo data = r.getData(new TypeReference<SpuInfoVo>() {
        });
        itemEntity.setSpuId(data.getId());
        itemEntity.setSpuBrand(data.getBrandId().toString());
        itemEntity.setSpuName(data.getSpuName());
        itemEntity.setCategoryId(data.getCatalogId());
        //3????????????sku??????
        itemEntity.setSkuId(cartItem.getSkuId());
        itemEntity.setSkuName(cartItem.getTitle());
        itemEntity.setSkuPic(cartItem.getImage());
        itemEntity.setSkuPrice(cartItem.getPrice());
        String skuAttr = StringUtils.collectionToDelimitedString(cartItem.getSkuAttr(), ";");
        itemEntity.setSkuAttrsVals(skuAttr);
        itemEntity.setSkuQuantity(cartItem.getCount());
        //4???????????????????????????
        //5???????????????
        itemEntity.setGiftGrowth(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount().toString())).intValue());
        itemEntity.setGiftIntegration(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount().toString())).intValue());
        //6???????????????????????????
        itemEntity.setPromotionAmount(new BigDecimal("0"));
        itemEntity.setCouponAmount(new BigDecimal("0"));
        itemEntity.setIntegrationAmount(new BigDecimal("0"));
        //??????????????????????????????,??????-????????????
        BigDecimal origin = itemEntity.getSkuPrice().multiply(new BigDecimal(itemEntity.getSkuQuantity().toString()));
        BigDecimal subtract = origin.subtract(itemEntity.getCouponAmount())
                .subtract(itemEntity.getPromotionAmount())
                .subtract(itemEntity.getIntegrationAmount());

        itemEntity.setRealAmount(subtract);

        return itemEntity;
    }
}