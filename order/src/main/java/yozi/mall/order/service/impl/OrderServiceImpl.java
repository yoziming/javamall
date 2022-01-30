package yozi.mall.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import yozi.mall.common.constant.OrderStatusEnum;
import yozi.mall.common.constant.PayConstant;
import yozi.mall.common.exception.NoStockException;
import yozi.mall.common.feign.CartFeignService;
import yozi.mall.common.feign.MemberFeignService;
import yozi.mall.common.feign.ProductFeignService;
import yozi.mall.common.feign.WareFeignService;
import yozi.mall.common.to.*;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.common.utils.R;
import yozi.mall.order.dao.OrderDao;
import yozi.mall.order.entity.OrderEntity;
import yozi.mall.order.entity.OrderItemEntity;
import yozi.mall.order.entity.PaymentInfoEntity;
import yozi.mall.order.interceptor.LoginUserInterceptor;
import yozi.mall.order.service.OrderItemService;
import yozi.mall.order.service.OrderService;
import yozi.mall.order.service.PaymentInfoService;
import yozi.mall.order.vo.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static yozi.mall.common.constant.CartConstant.CART_PREFIX;
import static yozi.mall.common.constant.OrderConstant.USER_ORDER_TOKEN_PREFIX;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    // @Override
    // public PageUtils queryPage(Map<String, Object> params) {
    //     IPage<OrderEntity> page = this.page(
    //             new Query<OrderEntity>().getPage(params),
    //             new QueryWrapper<OrderEntity>()
    //     );
    //
    //     return new PageUtils(page);
    // }
    private ThreadLocal<OrderSubmitVo> confirmVoThreadLocal = new ThreadLocal<>();

    @Autowired
    private MemberFeignService memberFeignService;

    @Autowired
    private CartFeignService cartFeignService;

    @Autowired
    private WareFeignService wareFeignService;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PaymentInfoService paymentInfoService;

    // @Autowired
    // private BestPayService bestPayService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 訂單確認頁返回需要用的數據
     *
     * @return
     */
    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
        // 構建OrderConfirmVo
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        // 獲取當前用戶登入的信息
        MemberResponseTo memberResponseTo = LoginUserInterceptor.loginUser.get();
        // 獲取當前線程請求頭信息(解決Feign異步調用丟失請求頭問題)
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 開啟第一個異步任務
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            // 每一個線程都來共享之前的請求數據
            RequestContextHolder.setRequestAttributes(requestAttributes);
            // 遠程查詢所有的收貨地址列表
            List<MemberAddressTo> address = memberFeignService.getAddress(memberResponseTo.getId());
            confirmVo.setMemberAddressTos(address);
        }, threadPoolExecutor);

        // 開啟第二個異步任務
        CompletableFuture<Void> cartInfoFuture = CompletableFuture.runAsync(() -> {
            // 每一個線程都來共享之前的請求數據【解決異步ThreadLocal 無法共享數據】
            RequestContextHolder.setRequestAttributes(requestAttributes);
            // 遠程查詢購物車所有選中的購物項
            List<OrderItemTo> currentCartItems = cartFeignService.getCurrentCartItems();
            confirmVo.setItems(currentCartItems);
            // feign在遠程調用之前要構造請求，會調用很多的攔截器
        }, threadPoolExecutor).thenRunAsync(() -> {
            List<OrderItemTo> items = confirmVo.getItems();
            // 獲取全部商品的id
            List<Long> skuIds = items.stream()
                    .map((itemVo -> itemVo.getSkuId()))
                    .collect(Collectors.toList());

            // 遠程查詢商品庫存信息
            R skuHasStock = wareFeignService.getSkuHasStock(skuIds);
            List<SkuStockVo> skuStockVos = skuHasStock.getData("data", new TypeReference<List<SkuStockVo>>() {
            });

            if (skuStockVos != null && skuStockVos.size() > 0) {
                // 將skuStockVos集合轉換為map
                Map<Long, Boolean> skuHasStockMap =
                        skuStockVos.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                confirmVo.setStocks(skuHasStockMap);
            }
        }, threadPoolExecutor);

        // 查詢用戶積分
        Integer integration = memberResponseTo.getIntegration();
        confirmVo.setIntegration(integration);

        // 價格數據計算寫在VO了

        // 防重令牌(防止表單重複提交)
        // 為用戶設置一個token，三十分鐘過期時間（存在redis）
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(USER_ORDER_TOKEN_PREFIX + memberResponseTo.getId(), token, 30,
                TimeUnit.MINUTES);
        confirmVo.setOrderToken(token);

        CompletableFuture.allOf(addressFuture, cartInfoFuture).get();

        return confirmVo;
    }

    /**
     * 提交訂單
     *
     * @param vo
     * @return
     */
    // @Transactional(isolation = Isolation.READ_COMMITTED) 設置事務的隔離級別
    // @Transactional(propagation = Propagation.REQUIRED)   設置事務的傳播級別
    // @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional
    @Override
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo vo) {

        confirmVoThreadLocal.set(vo);

        // 下單後準備返回的結果
        SubmitOrderResponseVo responseVo = new SubmitOrderResponseVo();
        responseVo.setCode(0); // 預設返回0是成功
        // 獲取當前用戶登入的信息
        MemberResponseTo memberResponseTo = LoginUserInterceptor.loginUser.get();
        // 驗證令牌是否合法，令牌的對比和刪除必須保證原子性，LUA腳本
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return " +
                "0 end";
        // 拿前端傳來的token
        String orderToken = vo.getOrderToken();
        // 通過腳本原子驗證令牌和刪除令牌
        Long result = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                Arrays.asList(USER_ORDER_TOKEN_PREFIX + memberResponseTo.getId()),
                orderToken);
        // 若令牌驗證失敗，注意腳本0是失敗，跟前面SubmitOrderResponseVo 0=成功相反
        if (result == 0L) {
            responseVo.setCode(1);
            return responseVo;
        } else {
            // 令牌驗證成功，創建、下訂單、驗令牌、驗價格、鎖定庫存
            // 1、創建訂單、訂單項等信息
            OrderCreateTo order = createOrder();
            // 2、驗證價格
            BigDecimal payAmount = order.getOrder().getPayAmount();
            BigDecimal payPrice = vo.getPayPrice();

            if (Math.abs(payAmount.subtract(payPrice).doubleValue()) < 0.01) {
                // 金額對比，保存
                saveOrder(order);

                // 4、庫存鎖定,只要有異常，回滾訂單數據
                // 訂單號、所有訂單項信息(skuId,skuNum,skuName)
                WareSkuLockTo lockVo = new WareSkuLockTo();
                lockVo.setOrderSn(order.getOrder().getOrderSn());

                // 獲取出要鎖定的商品數據信息【order裡面存儲的是Entity】
                List<OrderItemTo> orderItemTos = order.getOrderItems().stream().map((item) -> {
                    OrderItemTo orderItemTo = new OrderItemTo();
                    orderItemTo.setSkuId(item.getSkuId());
                    orderItemTo.setCount(item.getSkuQuantity());
                    orderItemTo.setTitle(item.getSkuName());
                    return orderItemTo;
                }).collect(Collectors.toList());
                lockVo.setLocks(orderItemTos);

                // 出現的問題：扣減庫存成功了，但是由於網絡原因超時，出現異常，導致訂單事務回滾，庫存事務不回滾(解決方案：seata)
                // 為了保證高併發，不推薦使用seata，因為是加鎖，并行化，提升不了效率,可以發消息給庫存服務
                R r = wareFeignService.orderLockStock(lockVo);
                if (r.getCode() == 0) {
                    // 鎖定成功
                    responseVo.setOrder(order.getOrder());
                    //int i = 10/0;
                    // 訂單創建成功，發送消息給MQ
                    rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", order.getOrder());
                    // 刪除購物車裡的數據
                    redisTemplate.delete(CART_PREFIX + memberResponseTo.getId());
                    return responseVo;
                } else {
                    //鎖定失敗
                    String msg = (String) r.get("msg");
                    throw new NoStockException(msg);
                    // responseVo.setCode(3);
                    // return responseVo;
                }
            } else {
                responseVo.setCode(2);
                return responseVo;
            }
        }
    }

    /**
     * 按照訂單號獲取訂單信息
     */
    @Override
    public OrderEntity getOrderByOrderSn(String orderSn) {

        OrderEntity orderEntity = this.baseMapper.selectOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));

        return orderEntity;
    }

    /**
     * 關閉訂單
     */
    @Override
    public void closeOrder(OrderEntity orderEntity) {

        //關閉訂單之前先查詢一下數據庫，判斷此訂單狀態是否已支付
        OrderEntity orderInfo = this.getOne(new QueryWrapper<OrderEntity>().
                eq("order_sn", orderEntity.getOrderSn()));

        if (orderInfo.getStatus().equals(OrderStatusEnum.CREATE_NEW.getCode())) {
            //代付款狀態進行關單
            OrderEntity orderUpdate = new OrderEntity();
            orderUpdate.setId(orderInfo.getId());
            orderUpdate.setStatus(OrderStatusEnum.CANCLED.getCode());
            this.updateById(orderUpdate);

            // 發送消息給MQ
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(orderInfo, orderTo);

            try {
                //TODO 確保每個消息發送成功，給每個消息做好日誌記錄，(給數據庫保存每一個詳細信息)保存每個消息的詳細信息
                //TODO 定期掃描數據庫，重新發送失敗的消息
                rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other", orderTo);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 獲取當前訂單的支付信息
     */
    @Override
    public PayVo getOrderPay(String orderSn) {

        PayVo payVo = new PayVo();
        OrderEntity orderInfo = this.getOrderByOrderSn(orderSn);

        //保留兩位小數點，向上取值
        BigDecimal payAmount = orderInfo.getPayAmount().setScale(2, BigDecimal.ROUND_UP);
        payVo.setTotal_amount(payAmount.toString());
        payVo.setOut_trade_no(orderInfo.getOrderSn());

        //查詢訂單項的數據
        List<OrderItemEntity> orderItemInfo = orderItemService.list(
                new QueryWrapper<OrderItemEntity>().eq("order_sn", orderSn));
        OrderItemEntity orderItemEntity = orderItemInfo.get(0);
        payVo.setBody(orderItemEntity.getSkuAttrsVals());

        payVo.setSubject(orderItemEntity.getSkuName());

        return payVo;
    }

    /**
     * 查詢當前用戶所有訂單數據
     */
    @Override
    public PageUtils queryPageWithItem(Map<String, Object> params) {

        MemberResponseTo memberResponseVo = LoginUserInterceptor.loginUser.get();

        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
                        .eq("member_id", memberResponseVo.getId()).orderByDesc("create_time")
        );

        //遍歷所有訂單集合
        List<OrderEntity> orderEntityList = page.getRecords().stream().map(order -> {
            //根據訂單號查詢訂單項里的數據
            List<OrderItemEntity> orderItemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>()
                    .eq("order_sn", order.getOrderSn()));
            // order.setOrderItemEntityList(orderItemEntities);
            return order;
        }).collect(Collectors.toList());

        page.setRecords(orderEntityList);

        return new PageUtils(page);
    }

    /**
     * 保存訂單所有數據
     */
    private void saveOrder(OrderCreateTo orderCreateTo) {

        //獲取訂單信息
        OrderEntity order = orderCreateTo.getOrder();
        order.setModifyTime(new Date());
        order.setCreateTime(new Date());
        //保存訂單
        this.baseMapper.insert(order);

        //獲取訂單項信息
        List<OrderItemEntity> orderItems = orderCreateTo.getOrderItems();
        //批量保存訂單項數據
        orderItemService.saveBatch(orderItems);
    }

    /**
     * 創建訂單
     */
    private OrderCreateTo createOrder() {
        OrderCreateTo createTo = new OrderCreateTo();
        // 1、生成訂單號，用mybatis給的IdWorker根據時間創立
        String orderSn = IdWorker.getTimeId();
        // 構建訂單數據【封裝價格】
        OrderEntity orderEntity = builderOrder(orderSn);
        // 2、獲取到所有的訂單項【封裝價格】
        List<OrderItemEntity> orderItemEntities = builderOrderItems(orderSn);
        // 3、驗價(計算價格、積分等信息)
        computePrice(orderEntity, orderItemEntities);
        createTo.setOrder(orderEntity);
        createTo.setOrderItems(orderItemEntities);
        return createTo;
    }

    /**
     * 計算價格的方法
     *
     * @param orderEntity
     * @param orderItemEntities
     */
    private void computePrice(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities) {

        //總價
        BigDecimal total = new BigDecimal("0.0");
        //優惠價
        BigDecimal coupon = new BigDecimal("0.0");
        BigDecimal intergration = new BigDecimal("0.0");
        BigDecimal promotion = new BigDecimal("0.0");

        //積分、成長值
        Integer integrationTotal = 0;
        Integer growthTotal = 0;

        //訂單總額，疊加每一個訂單項的總額信息
        for (OrderItemEntity orderItem : orderItemEntities) {
            //優惠價格信息
            coupon = coupon.add(orderItem.getCouponAmount());
            promotion = promotion.add(orderItem.getPromotionAmount());
            intergration = intergration.add(orderItem.getIntegrationAmount());

            //總價
            total = total.add(orderItem.getRealAmount());

            //積分信息和成長值信息
            integrationTotal += orderItem.getGiftIntegration();
            growthTotal += orderItem.getGiftGrowth();

        }
        //1、訂單價格相關的
        orderEntity.setTotalAmount(total);
        //設置應付總額(總額+運費)
        orderEntity.setPayAmount(total.add(orderEntity.getFreightAmount()));
        orderEntity.setCouponAmount(coupon);
        orderEntity.setPromotionAmount(promotion);
        orderEntity.setIntegrationAmount(intergration);

        //設置積分成長值信息
        orderEntity.setIntegration(integrationTotal);
        orderEntity.setGrowth(growthTotal);

        //設置刪除狀態(0-未刪除，1-已刪除)
        orderEntity.setDeleteStatus(0);

    }

    /**
     * 構建訂單數據
     *
     * @param orderSn
     * @return
     */
    private OrderEntity builderOrder(String orderSn) {

        // 獲取當前用戶登入信息
        MemberResponseTo memberResponseTo = LoginUserInterceptor.loginUser.get();

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setMemberId(memberResponseTo.getId());
        orderEntity.setOrderSn(orderSn);
        orderEntity.setMemberUsername(memberResponseTo.getUsername());

        OrderSubmitVo orderSubmitVo = confirmVoThreadLocal.get();

        // 遠程獲取收貨地址和運費信息
        R fareAddressVo = wareFeignService.getFare(orderSubmitVo.getAddrId());
        FareVo fareResp = fareAddressVo.getData("data", new TypeReference<FareVo>() {
        });

        // 獲取到運費信息
        BigDecimal fare = fareResp.getFare();
        orderEntity.setFreightAmount(fare);

        // 獲取到收貨地址信息
        MemberAddressTo address = fareResp.getAddress();
        // 設置收貨人信息
        orderEntity.setReceiverName(address.getName());
        orderEntity.setReceiverPhone(address.getPhone());
        orderEntity.setReceiverPostCode(address.getPostCode());
        orderEntity.setReceiverProvince(address.getProvince());
        orderEntity.setReceiverCity(address.getCity());
        orderEntity.setReceiverRegion(address.getRegion());
        orderEntity.setReceiverDetailAddress(address.getDetailAddress());

        // 設置訂單相關的狀態信息
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        orderEntity.setAutoConfirmDay(7);
        orderEntity.setConfirmStatus(0);
        return orderEntity;
    }

    /**
     * 構建所有訂單項數據
     *
     * @return
     */
    public List<OrderItemEntity> builderOrderItems(String orderSn) {

        List<OrderItemEntity> orderItemEntityList = new ArrayList<>();

        // 最後確定每個購物項的價格
        List<OrderItemTo> currentCartItems = cartFeignService.getCurrentCartItems();
        if (currentCartItems != null && currentCartItems.size() > 0) {
            orderItemEntityList = currentCartItems.stream().map((items) -> {
                // 構建訂單項數據
                OrderItemEntity orderItemEntity = builderOrderItem(items);
                orderItemEntity.setOrderSn(orderSn);

                return orderItemEntity;
            }).collect(Collectors.toList());
        }

        return orderItemEntityList;
    }

    /**
     * 構建某一個訂單項的數據
     *
     * @param items
     * @return
     */
    private OrderItemEntity builderOrderItem(OrderItemTo items) {

        OrderItemEntity orderItemEntity = new OrderItemEntity();

        //1、商品的spu信息
        Long skuId = items.getSkuId();
        //獲取spu的信息
        R spuInfo = productFeignService.getSpuInfoBySkuId(skuId);
        SpuInfoTo spuInfoData = spuInfo.getData("data", new TypeReference<SpuInfoTo>() {
        });
        orderItemEntity.setSpuId(spuInfoData.getId());
        orderItemEntity.setSpuName(spuInfoData.getSpuName());
        orderItemEntity.setSpuBrand(spuInfoData.getBrandName());
        orderItemEntity.setCategoryId(spuInfoData.getCatalogId());

        //2、商品的sku信息
        orderItemEntity.setSkuId(skuId);
        orderItemEntity.setSkuName(items.getTitle());
        orderItemEntity.setSkuPic(items.getImage());
        orderItemEntity.setSkuPrice(items.getPrice());
        orderItemEntity.setSkuQuantity(items.getCount());

        //使用StringUtils.collectionToDelimitedString將list集合轉換為String
        String skuAttrValues = StringUtils.collectionToDelimitedString(items.getSkuAttrValues(), ";");
        orderItemEntity.setSkuAttrsVals(skuAttrValues);

        //3、商品的優惠信息

        //4、商品的積分信息
        orderItemEntity.setGiftGrowth(items.getPrice().multiply(new BigDecimal(items.getCount())).intValue());
        orderItemEntity.setGiftIntegration(items.getPrice().multiply(new BigDecimal(items.getCount())).intValue());

        //5、訂單項的價格信息
        orderItemEntity.setPromotionAmount(BigDecimal.ZERO);
        orderItemEntity.setCouponAmount(BigDecimal.ZERO);
        orderItemEntity.setIntegrationAmount(BigDecimal.ZERO);

        //當前訂單項的實際金額.總額 - 各種優惠價格
        //原來的價格
        BigDecimal origin =
                orderItemEntity.getSkuPrice().multiply(new BigDecimal(orderItemEntity.getSkuQuantity().toString()));
        //原價減去優惠價得到最終的價格
        BigDecimal subtract = origin.subtract(orderItemEntity.getCouponAmount())
                .subtract(orderItemEntity.getPromotionAmount())
                .subtract(orderItemEntity.getIntegrationAmount());
        orderItemEntity.setRealAmount(subtract);

        return orderItemEntity;
    }

    /**
     * 處理支付寶的支付結果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String handlePayResult(PayAsyncVo asyncVo) {

        //保存交易流水信息
        PaymentInfoEntity paymentInfo = new PaymentInfoEntity();
        paymentInfo.setOrderSn(asyncVo.getOut_trade_no());
        paymentInfo.setAlipayTradeNo(asyncVo.getTrade_no());
        paymentInfo.setTotalAmount(new BigDecimal(asyncVo.getBuyer_pay_amount()));
        paymentInfo.setSubject(asyncVo.getBody());
        paymentInfo.setPaymentStatus(asyncVo.getTrade_status());
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setCallbackTime(asyncVo.getNotify_time());
        //添加到數據庫中
        this.paymentInfoService.save(paymentInfo);

        //修改訂單狀態
        //獲取當前狀態
        String tradeStatus = asyncVo.getTrade_status();

        if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
            //支付成功狀態
            String orderSn = asyncVo.getOut_trade_no(); //獲取訂單號
            this.updateOrderStatus(orderSn, OrderStatusEnum.PAYED.getCode(), PayConstant.ALIPAY);
        }
        return "success";
    }

    /**
     * 修改訂單狀態
     *
     * @param orderSn
     * @param code
     */
    private void updateOrderStatus(String orderSn, Integer code, Integer payType) {

        // this.baseMapper.updateOrderStatus(orderSn, code, payType);
    }

    // /**
    //  * 微信異步通知結果
    //  *
    //  * @param notifyData
    //  * @return
    //  */
    // @Override
    // public String asyncNotify(String notifyData) {
    //
    //     //簽名效驗
    //     PayResponse payResponse = bestPayService.asyncNotify(notifyData);
    //     log.info("payResponse={}", payResponse);
    //
    //     //2.金額效驗（從數據庫查訂單）
    //     OrderEntity orderEntity = this.getOrderByOrderSn(payResponse.getOrderId());
    //
    //     //如果查詢出來的數據是null的話
    //     //比較嚴重(正常情況下是不會發生的)發出告警：釘釘、短信
    //     if (orderEntity == null) {
    //         //TODO 發出告警，釘釘，短信
    //         throw new RuntimeException("通過訂單編號查詢出來的結果是null");
    //     }
    //
    //     //判斷訂單狀態狀態是否為已支付或者是已取消,如果不是訂單狀態不是已支付狀態
    //     Integer status = orderEntity.getStatus();
    //     if (status.equals(OrderStatusEnum.PAYED.getCode()) || status.equals(OrderStatusEnum.CANCLED.getCode())) {
    //         throw new RuntimeException("該訂單已失效,orderNo=" + payResponse.getOrderId());
    //     }
    //
    //     /*//判斷金額是否一致,Double類型比較大小，精度問題不好控制
    //     if (orderEntity.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0) {
    //         //TODO 告警
    //         throw new RuntimeException("異步通知中的金額和數據庫里的不一致,orderNo=" + payResponse.getOrderId());
    //     }*/
    //
    //     //3.修改訂單支付狀態
    //     //支付成功狀態
    //     String orderSn = orderEntity.getOrderSn();
    //     this.updateOrderStatus(orderSn, OrderStatusEnum.PAYED.getCode(), PayConstant.WXPAY);
    //
    //     //4.告訴微信不要再重複通知了
    //     return "<xml>\n" +
    //             "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
    //             "  <return_msg><![CDATA[OK]]></return_msg>\n" +
    //             "</xml>";
    // }
    //
    // /**
    //  * 創建秒殺單
    //  *
    //  * @param orderTo
    //  */
    // @Override
    // public void createSeckillOrder(SeckillOrderTo orderTo) {
    //
    //     //TODO 保存訂單信息
    //     OrderEntity orderEntity = new OrderEntity();
    //     orderEntity.setOrderSn(orderTo.getOrderSn());
    //     orderEntity.setMemberId(orderTo.getMemberId());
    //     orderEntity.setCreateTime(new Date());
    //     BigDecimal totalPrice = orderTo.getSeckillPrice().multiply(BigDecimal.valueOf(orderTo.getNum()));
    //     orderEntity.setPayAmount(totalPrice);
    //     orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
    //
    //     //保存訂單
    //     this.save(orderEntity);
    //
    //     //保存訂單項信息
    //     OrderItemEntity orderItem = new OrderItemEntity();
    //     orderItem.setOrderSn(orderTo.getOrderSn());
    //     orderItem.setRealAmount(totalPrice);
    //
    //     orderItem.setSkuQuantity(orderTo.getNum());
    //
    //     //保存商品的spu信息
    //     R spuInfo = productFeignService.getSpuInfoBySkuId(orderTo.getSkuId());
    //     SpuInfoVo spuInfoData = spuInfo.getData("data", new TypeReference<SpuInfoVo>() {
    //     });
    //     orderItem.setSpuId(spuInfoData.getId());
    //     orderItem.setSpuName(spuInfoData.getSpuName());
    //     orderItem.setSpuBrand(spuInfoData.getBrandName());
    //     orderItem.setCategoryId(spuInfoData.getCatalogId());
    //
    //     //保存訂單項數據
    //     orderItemService.save(orderItem);
    // }
    //
    // public static void main(String[] args) {
    //     String orderSn = IdWorker.getTimeId().substring(0, 16);
    //     System.out.println(orderSn);
    // }
}