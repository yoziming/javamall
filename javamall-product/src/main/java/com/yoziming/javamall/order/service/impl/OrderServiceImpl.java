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
     * 訂單確認頁返回需要用的數據
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
                //1、查詢所有收貨地址列表
                //設置主線程中的數據
                List<MemberReceiveAddressEntity> addressEntities =
                        memberReceiveAddressController.getAddress(memberRespVo.getId());
                confirmVo.setAddress(addressEntities);
            }, executor);

            CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
                //2、查詢購物車所有選中的購物項
                //設置主線程中的數據

                List<CartItem> cartItems = cartService.getUserCartItems(memberRespVo);

                confirmVo.setItems(cartItems);
                //feign在遠程調用之前要構造請求，調用很多的攔截器
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

            //3、查詢用戶積分
            Integer integration = memberRespVo.getIntegration();
            confirmVo.setIntegration(integration == null ? 0 : integration);

            //防重令牌
            String token = UUID.randomUUID().toString().replace("-", "");
            // 令牌添加入redis
            redisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberRespVo.getId(), token, 30,
                    TimeUnit.MINUTES);
            confirmVo.setOrderToken(token);

            CompletableFuture.allOf(getAddressFuture, cartFuture).get();

            return confirmVo;
        } catch (Exception e) {
            log.error("生成訂單確認頁失敗：", e);
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

        //1、驗證令牌【令牌的對比和刪除必須保證原子性】
        //0令牌失敗，1刪除成功
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 " +
                "end";
        String orderToken = vo.getOrderToken();

        //原子驗證令牌和刪除令牌
        Long result = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                Arrays.asList(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberRespVo.getId()), orderToken);
        if (result == 0L) {
            //失敗
            response.setCode(1);
            return response;
        } else {
            //令牌驗證成功
            //1、創建訂單，訂單項等訊息
            OrderCreateTo order = createOrder(memberRespVo);
            //2、驗價
            BigDecimal payAmount = order.getOrder().getPayAmount();
            BigDecimal payPrice = vo.getPayPrice();
            if (Math.abs(payAmount.subtract(payPrice).doubleValue()) < 0.01) {
                //金額對比
                //3、保存訂單
                saveOrder(order);
                //4、庫存鎖定,只要有異常回滾訂單數據。
                // 訂單號，所有訂單項（skuId,skuName,num）
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

                //問題：庫存成功了，但是網絡原因超時了，訂單回滾，庫存不回滾
                //為了保證高併發，庫存服務自己回滾，可以發消息給庫存服務；
                //庫存服務本身也可以自動解鎖模式 使用消息隊列
                R r = wareSkuController.orderLockStock(lockVo1);
                if (r.getCode() == 0) {
                    //鎖成功了
                    response.setOrder(order.getOrder());
                    rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", order.getOrder());
                    return response;
                } else {
                    //鎖定失敗
                    log.error("商品庫存不足，OrderSubmitVo={}", vo);
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
     * 獲取訂單詳情頁訊息
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
            //已發貨狀態
            order.setDeliveryTime(new Date());
            if (StringUtils.isEmpty(order.getDeliveryCompany())) {
                order.setDeliveryCompany("普通快遞");
            }
        }
        if (order.getStatus() == OrderStatusEnum.SERVICED.getCode()) {
            // 更新為售後完成狀態
            // 解鎖庫存
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(order, orderTo);
            try {
                rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other", order);
            } catch (AmqpException e) {
                log.error("訂單售後完成：發送解鎖庫存消息失敗！", e);
                rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other", order);
            }
        }
        order.setModifyTime(new Date());
        this.updateById(order);

    }

    @Override
    public void forcePaySuccess(String orderSn) {

        //1、保存交易流水
        PaymentInfoEntity infoEntity = new PaymentInfoEntity();
        infoEntity.setOrderSn(orderSn);
        infoEntity.setPaymentStatus("TRADE_SUCCESS");
        infoEntity.setCallbackTime(new Date());

        paymentInfoService.save(infoEntity);

        //2、修改訂單的狀態訊息
        //支付成功狀態，訂單號就是支付號
        this.baseMapper.updateOrderStatus(orderSn, OrderStatusEnum.PAYED.getCode());

    }

    @Override
    public void closeOrder(OrderEntity entity) {
        // 更新支付狀態
        String payResult = handlePayResult(entity.getOrderSn());
        if (payResult.equals("success")) {
            log.info("訂單已支付，無需取消訂單。");
            return;
        }
        //查詢當前這個訂單的最新狀態
        OrderEntity orderEntity = this.getById(entity.getId());
        if (orderEntity.getStatus() == OrderStatusEnum.CREATE_NEW.getCode()) {
            //關單
            OrderEntity update = new OrderEntity();
            update.setId(entity.getId());
            update.setStatus(OrderStatusEnum.CANCEL.getCode());
            this.updateById(update);
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(orderEntity, orderTo);
            //發給MQ一個
            try {
                //關單後解鎖庫存
                log.info("發送關單解鎖庫存消息！orderTo={}", orderTo);
                rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other.unlock", orderTo);
            } catch (Exception e) {
                log.error("發送關單解鎖庫存消息失敗！", e);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelOrder(String orderSn) {
        //查詢當前這個訂單的最新狀態
        OrderEntity orderEntity = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
        if (orderEntity.getStatus() == OrderStatusEnum.PAYED.getCode()) {
            // 關單，解鎖庫存
            OrderEntity update = new OrderEntity();
            update.setId(orderEntity.getId());
            update.setStatus(OrderStatusEnum.CANCEL.getCode());
            this.updateById(update);
            OrderTo orderTo = new OrderTo();
            orderTo.setOrderSn(orderSn);
            log.info("發送關單解鎖庫存消息！orderTo={}", orderTo);
            rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other.unlock", orderTo);
            // 退款
            RefundInfoEntity refundInfo = new RefundInfoEntity();
            // AlipayTradeRefundResponse response = alipayTemplate.refund(orderSn, orderEntity.getPayAmount());
            // if (response.isSuccess()) {
            //     // 保存退款訊息
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
            //     throw new RuntimeException("退款失敗");
            // }
        } else if (orderEntity.getStatus() == OrderStatusEnum.CREATE_NEW.getCode()) {
            // 待支付
            //關單
            closeOrder(orderEntity);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirmReceipt(String orderSn) {
        //查詢當前這個訂單的最新狀態
        OrderEntity orderEntity = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
        if (orderEntity.getStatus() == OrderStatusEnum.SENDED.getCode()) {
            //確認收貨
            OrderEntity update = new OrderEntity();
            update.setId(orderEntity.getId());
            update.setStatus(OrderStatusEnum.RECEIVED.getCode());
            update.setConfirmStatus(1);
            this.updateById(update);
            // 減去庫存
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(orderEntity, orderTo);
            try {
                // 減庫存可以用 異步 的 消息隊列
                //                wmsFeignService.minusWare(orderEntity);
                log.info("減庫存消息發送！");
                rabbitTemplate.convertAndSend("stock-event-exchange", "stock.minus", orderTo);
            } catch (Exception e) {
                log.error("減庫存消息發送失敗！", e);
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
     * 處理支付結果
     *
     * @param vo
     * @return
     */
    @Override
    public String handlePayResult(PayAsyncVo vo) {
        //1、保存交易流水
        PaymentInfoEntity infoEntity = new PaymentInfoEntity();
        infoEntity.setAlipayTradeNo(vo.getTrade_no());
        infoEntity.setOrderSn(vo.getOut_trade_no());
        infoEntity.setPaymentStatus(vo.getTrade_status());
        infoEntity.setCallbackTime(vo.getNotify_time());

        paymentInfoService.save(infoEntity);

        //2、修改訂單的狀態訊息
        if (vo.getTrade_status().equals("TRADE_SUCCESS") || vo.getTrade_status().equals("TRADE_FINISHED")) {
            //支付成功狀態
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
        //         log.info("支付寶訂單查詢接口,PayAmount={}, TradeStatus={}, OutTradeNo={}, BuyerLogonId={}, BuyerPayAmount={}",
        //                 response.getPayAmount(), response.getTradeStatus(), response.getOutTradeNo(),
        //                 response.getBuyerLogonId(), response.getBuyerPayAmount());
        //         String tradeStatus = response.getTradeStatus();
        //
        //         //1、保存交易流水
        //         PaymentInfoEntity infoEntity = new PaymentInfoEntity();
        //         infoEntity.setAlipayTradeNo(response.getTradeNo());
        //         infoEntity.setOrderSn(response.getOutTradeNo());
        //         infoEntity.setPaymentStatus(response.getTradeStatus());
        //         infoEntity.setCallbackTime(response.getSendPayDate());
        //         infoEntity.setCreateTime(new Date());
        //         infoEntity.setSubject(response.getSubject());
        //         infoEntity.setTotalAmount(new BigDecimal(response.getBuyerPayAmount()));
        //         // 響應訊息太長
        //         //                infoEntity.setCallbackContent(JSONObject.toJSONString(response));
        //
        //         paymentInfoService.save(infoEntity);
        //         // 支付成功
        //         if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
        //             //支付成功狀態
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
     * 保存訂單數據
     *
     * @param order
     */
    private void saveOrder(OrderCreateTo order) {
        OrderEntity orderEntity = order.getOrder();
        this.save(orderEntity);

        List<OrderItemEntity> orderItems = order.getOrderItems();
        //        orderItemService.saveBatch(orderItems);
        //使用批量保存（沒有導入seata前正常）時出現問題：
        // Error updating database.  Cause: io.seata.common.exception.NotSupportYetException
        //使用普通的保存方法，不使用seata可重新修改回來
        for (OrderItemEntity orderItem : orderItems) {
            orderItemDao.insert(orderItem);
        }
    }

    private OrderCreateTo createOrder(MemberRespVo memberRespVo) {
        OrderCreateTo orderCreateTo = new OrderCreateTo();
        //1、生成訂單號
        String orderSn = IdWorker.getTimeId();
        OrderEntity orderEntity = buildOrder(orderSn);

        //2、獲取到所有的訂單項
        List<OrderItemEntity> itemEntities = buildOrderItems(orderSn, memberRespVo);

        //3、驗價,計算價格相關
        computePrice(orderEntity, itemEntities);
        orderCreateTo.setOrder(orderEntity);
        orderCreateTo.setOrderItems(itemEntities);
        return orderCreateTo;
    }

    private void computePrice(OrderEntity orderEntity, List<OrderItemEntity> itemEntities) {
        BigDecimal total = new BigDecimal("0.0");
        //訂單的總額，疊加每個訂單項的總額訊息
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
        //1、訂單價格相關
        orderEntity.setTotalAmount(total);
        //應付總額
        orderEntity.setPayAmount(total.add(orderEntity.getFreightAmount()));
        orderEntity.setPromotionAmount(promotion);
        orderEntity.setIntegrationAmount(integration);
        orderEntity.setCouponAmount(coupon);

        //設置積分等訊息
        orderEntity.setIntegration(gift.intValue());
        orderEntity.setGrowth(growth.intValue());
        orderEntity.setDeleteStatus(0); //0:未刪除
    }

    private OrderEntity buildOrder(String orderSn) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        OrderEntity entity = new OrderEntity();
        entity.setOrderSn(orderSn);
        entity.setMemberId(memberRespVo.getId());
        entity.setMemberUsername(memberRespVo.getUsername());
        //獲取收貨地址訊息
        OrderSubmitVo orderSubmitVo = submitVoThreadLocal.get();
        MemberReceiveAddressEntity memberReceiveAddress =
                memberReceiveAddressService.getById(orderSubmitVo.getAddrId());
        //設置運費訊息
        entity.setFreightAmount(BigDecimal.ZERO);
        //設置收貨人訊息
        entity.setReceiverCity(memberReceiveAddress.getCity());
        entity.setReceiverDetailAddress(memberReceiveAddress.getDetailAddress());
        entity.setReceiverName(memberReceiveAddress.getName());
        entity.setReceiverPhone(memberReceiveAddress.getPhone());
        entity.setReceiverProvince(memberReceiveAddress.getProvince());
        entity.setReceiverRegion(memberReceiveAddress.getRegion());
        entity.setReceiverPostCode(memberReceiveAddress.getPostCode());

        //設置訂單的狀態訊息
        entity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        entity.setAutoConfirmDay(7);
        return entity;
    }

    /**
     * 構建所有訂單項數據
     *
     * @return
     */
    private List<OrderItemEntity> buildOrderItems(String orderSn, MemberRespVo memberRespVo) {
        //最後確認每個購物項的價格
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
     * 構建某一個訂單
     *
     * @param cartItem
     * @return
     */
    private OrderItemEntity buildOrderItem(CartItem cartItem) {
        OrderItemEntity itemEntity = new OrderItemEntity();
        //1、訂單訊息：訂單號
        //2、商品的SPU訊息
        Long skuId = cartItem.getSkuId();
        R r = spuInfoController.getSpuInfoBySkuId(skuId);
        SpuInfoVo data = r.getData(new TypeReference<SpuInfoVo>() {
        });
        itemEntity.setSpuId(data.getId());
        itemEntity.setSpuBrand(data.getBrandId().toString());
        itemEntity.setSpuName(data.getSpuName());
        itemEntity.setCategoryId(data.getCatalogId());
        //3、商品的sku訊息
        itemEntity.setSkuId(cartItem.getSkuId());
        itemEntity.setSkuName(cartItem.getTitle());
        itemEntity.setSkuPic(cartItem.getImage());
        itemEntity.setSkuPrice(cartItem.getPrice());
        String skuAttr = StringUtils.collectionToDelimitedString(cartItem.getSkuAttr(), ";");
        itemEntity.setSkuAttrsVals(skuAttr);
        itemEntity.setSkuQuantity(cartItem.getCount());
        //4、優惠訊息（不做）
        //5、積分訊息
        itemEntity.setGiftGrowth(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount().toString())).intValue());
        itemEntity.setGiftIntegration(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount().toString())).intValue());
        //6、訂單項的價格訊息
        itemEntity.setPromotionAmount(new BigDecimal("0"));
        itemEntity.setCouponAmount(new BigDecimal("0"));
        itemEntity.setIntegrationAmount(new BigDecimal("0"));
        //當前訂單項的實際金額,總額-各種優惠
        BigDecimal origin = itemEntity.getSkuPrice().multiply(new BigDecimal(itemEntity.getSkuQuantity().toString()));
        BigDecimal subtract = origin.subtract(itemEntity.getCouponAmount())
                .subtract(itemEntity.getPromotionAmount())
                .subtract(itemEntity.getIntegrationAmount());

        itemEntity.setRealAmount(subtract);

        return itemEntity;
    }
}