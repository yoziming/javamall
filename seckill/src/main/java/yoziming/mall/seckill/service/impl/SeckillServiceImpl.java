package yoziming.mall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import yoziming.mall.common.feign.CouponFeignService;
import yoziming.mall.common.feign.ProductFeignService;
import yoziming.mall.common.to.MemberResponseTo;
import yoziming.mall.common.to.SeckillSkuRedisTo;
import yoziming.mall.common.to.mq.SeckillOrderTo;
import yoziming.mall.common.utils.R;
import yoziming.mall.common.vo.SkuInfoVo;
import yoziming.mall.seckill.interceptor.LoginUserInterceptor;
import yoziming.mall.seckill.service.SeckillService;
import yoziming.mall.seckill.vo.SeckillSessionWithSkusVo;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final String SESSION__CACHE_PREFIX = "seckill:sessions:";

    private final String SECKILL_CHARE_PREFIX = "seckill:skus";

    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";    //+商品隨機碼

    @Override
    public void uploadSeckillSkuLatest3Days() {

        // 掃描最近三天的商品需要參加秒殺的活動
        R lates3DaySession = couponFeignService.getLates3DaySession();
        if (lates3DaySession.getCode() == 0) {
            // 上架商品
            List<SeckillSessionWithSkusVo> sessionData = lates3DaySession.getData("data",
                    new TypeReference<List<SeckillSessionWithSkusVo>>() {
                    });
            // 緩存到Redis
            // 緩存活動信息
            saveSessionInfos(sessionData);
            // 緩存活動的關聯商品信息
            saveSessionSkuInfo(sessionData);
        }
    }

    /**
     * 緩存秒殺活動信息
     *
     * @param sessions
     */
    private void saveSessionInfos(List<SeckillSessionWithSkusVo> sessions) {
        if (!CollectionUtils.isEmpty(sessions))
            sessions.stream().forEach(session -> {

                // 獲取當前活動的開始和結束時間的時間戳
                long startTime = session.getStartTime().getTime();
                long endTime = session.getEndTime().getTime();

                // 存入到Redis中的key
                String key = SESSION__CACHE_PREFIX + startTime + "_" + endTime;

                // 判斷Redis中是否有該信息，如果沒有才進行添加
                Boolean hasKey = redisTemplate.hasKey(key);
                // 緩存活動信息
                if (!hasKey) {
                    // 獲取到活動中所有商品的skuId
                    List<String> skuIds = session.getRelationSkus().stream()
                            .map(item -> item.getPromotionSessionId() + "-" + item.getSkuId().toString()).collect(Collectors.toList());
                    redisTemplate.opsForList().leftPushAll(key, skuIds);
                }
            });
    }

    /**
     * 緩存秒殺活動所關聯的商品信息
     */
    private void saveSessionSkuInfo(List<SeckillSessionWithSkusVo> sessions) {
        if (!CollectionUtils.isEmpty(sessions))
            sessions.stream().forEach(session -> {
                // 準備hash操作，綁定hash
                BoundHashOperations<String, Object, Object> operations =
                        redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
                session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                    // 生成隨機碼
                    String token = UUID.randomUUID().toString().replace("-", "");
                    String redisKey =
                            seckillSkuVo.getPromotionSessionId().toString() + "-" + seckillSkuVo.getSkuId().toString();
                    if (!operations.hasKey(redisKey)) {

                        // 緩存商品信息
                        SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
                        Long skuId = seckillSkuVo.getSkuId();
                        // 先查詢sku的基本信息，調用遠程服務
                        R info = productFeignService.getInfo(skuId);
                        if (info.getCode() == 0) {
                            SkuInfoVo skuInfo = info.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                            });
                            redisTo.setSkuInfo(skuInfo);
                        }

                        // sku的秒殺信息
                        BeanUtils.copyProperties(seckillSkuVo, redisTo);

                        // 設置當前商品的秒殺時間信息
                        redisTo.setStartTime(session.getStartTime().getTime());
                        redisTo.setEndTime(session.getEndTime().getTime());

                        // 設置商品的隨機碼（防止惡意攻擊）
                        redisTo.setRandomCode(token);

                        // 序列化json格式存入Redis中
                        String seckillValue = JSON.toJSONString(redisTo);
                        operations.put(seckillSkuVo.getPromotionSessionId().toString() + "-" + seckillSkuVo.getSkuId().toString(), seckillValue);

                        // 如果當前這個場次的商品庫存信息已經上架就不需要上架
                        // 使用庫存作為分佈式Redisson信號量（限流）
                        // 使用庫存作為分佈式信號量
                        RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                        // 商品可以秒殺的數量作為信號量
                        semaphore.trySetPermits(seckillSkuVo.getSeckillCount());
                    }
                });
            });
    }

    // public List<SeckillSkuRedisTo> blockHandler(BlockException e) {
    //     log.error("getCurrentSeckillSkusResource被限流了,{}", e.getMessage());
    //     return null;
    // }

    /**
     * 獲取到當前可以參加秒殺商品的信息
     */
    // @SentinelResource(value = "getCurrentSeckillSkusResource", blockHandler = "blockHandler")
    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        //
        // try (Entry entry = SphU.entry("seckillSkus")) {
        // 確定當前屬於哪個秒殺場次
        long currentTime = System.currentTimeMillis();

        // 從Redis中查詢到所有key以seckill:sessions開頭的所有數據
        Set<String> keys = redisTemplate.keys(SESSION__CACHE_PREFIX + "*");
        for (String key : keys) {
            // seckill:sessions:1594396764000_1594453242000
            String replace = key.replace(SESSION__CACHE_PREFIX, "");
            String[] s = replace.split("_");
            // 獲取存入Redis商品的開始時間
            long startTime = Long.parseLong(s[0]);
            // 獲取存入Redis商品的結束時間
            long endTime = Long.parseLong(s[1]);

            // 判斷是否是當前秒殺場次
            if (currentTime >= startTime && currentTime <= endTime) {
                // 獲取這個秒殺場次需要的所有商品信息
                List<String> range = redisTemplate.opsForList().range(key, -100, 100);
                BoundHashOperations<String, String, String> hasOps =
                        redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
                assert range != null;
                List<String> listValue = hasOps.multiGet(range);
                if (listValue != null && listValue.size() >= 0) {

                    List<SeckillSkuRedisTo> collect = listValue.stream().map(item -> {
                        String items = (String) item;
                        SeckillSkuRedisTo redisTo = JSON.parseObject(items, SeckillSkuRedisTo.class);
                        // redisTo.setRandomCode(null);若當前秒殺還沒開始，需要Ban掉隨機碼
                        return redisTo;
                    }).collect(Collectors.toList());
                    return collect;
                }
                break;
            }
        }
        // } catch (BlockException e) {
        //     log.error("資源被限流{}", e.getMessage());
        // }
        return null;
    }

    /**
     * 根據skuId查詢商品是否參加秒殺活動
     */
    @Override
    public SeckillSkuRedisTo getSkuSeckilInfo(Long skuId) {

        // 找到所有需要秒殺的商品的key信息---seckill:skus
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
        // 拿到所有的key
        Set<String> keys = hashOps.keys();
        if (keys != null && keys.size() > 0) {
            // 正則表達式進行匹配
            String reg = "\\d-" + skuId;
            for (String key : keys) {
                // 如果匹配上了
                if (Pattern.matches(reg, key)) {
                    // 從Redis中取出商品數據
                    String redisValue = hashOps.get(key);
                    // 進行序列化
                    SeckillSkuRedisTo redisTo = JSON.parseObject(redisValue, SeckillSkuRedisTo.class);

                    Long currentTime = System.currentTimeMillis();
                    Long startTime = redisTo.getStartTime();
                    Long endTime = redisTo.getEndTime();
                    // 如果當前時間大於等於秒殺活動開始時間並且要小於活動結束時間
                    if (currentTime >= startTime && currentTime <= endTime) {
                        return redisTo;
                    }
                    redisTo.setRandomCode(null);
                    return redisTo;
                }
            }
        }
        return null;
    }

    /**
     * 當前商品進行秒殺（秒殺開始）
     */
    @Override
    public String kill(String killId, String key, Integer num) throws InterruptedException {

        long s1 = System.currentTimeMillis();
        // 獲取當前用戶的信息
        MemberResponseTo user = LoginUserInterceptor.loginUser.get();

        // 獲取當前秒殺商品的詳細信息從Redis中獲取
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
        String skuInfoValue = hashOps.get(killId);
        if (StringUtils.isEmpty(skuInfoValue)) {
            return null;
        }
        // 合法性效驗
        SeckillSkuRedisTo redisTo = JSON.parseObject(skuInfoValue, SeckillSkuRedisTo.class);
        Long startTime = redisTo.getStartTime();
        Long endTime = redisTo.getEndTime();
        long currentTime = System.currentTimeMillis();
        // 判斷當前這個秒殺請求是否在活動時間區間內(效驗時間的合法性)
        if (currentTime >= startTime && currentTime <= endTime) {

            // 效驗隨機碼和商品id
            String randomCode = redisTo.getRandomCode();
            String skuId = redisTo.getPromotionSessionId() + "-" + redisTo.getSkuId();
            if (randomCode.equals(key) && killId.equals(skuId)) {
                // 驗證購物數量是否合理和庫存量是否充足
                Integer seckillLimit = redisTo.getSeckillLimit();

                // 獲取信號量
                String seckillCount = redisTemplate.opsForValue().get(SKU_STOCK_SEMAPHORE + randomCode);
                Integer count = Integer.valueOf(seckillCount);
                // 判斷信號量是否大於0,並且買的數量不能超過庫存
                if (count > 0 && num <= seckillLimit && count > num) {
                    // 驗證這個人是否已經買過了，如果秒殺成功，就去redis佔位，靠setIfAbsent(userId-sessionId-skuId
                    // SETNX 原子性處理
                    String redisKey = user.getId() + "-" + skuId;
                    // 設置自動過期(活動結束時間-當前時間)
                    Long ttl = endTime - currentTime;
                    Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl,
                            TimeUnit.MILLISECONDS);
                    if (aBoolean) {
                        // 佔位成功說明沒買過，要消費掉信號量，用try
                        RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + randomCode);
                        boolean semaphoreCount = semaphore.tryAcquire(num, 100, TimeUnit.MILLISECONDS);
                        // 保證Redis中還有商品庫存
                        if (semaphoreCount) {
                            // 創建訂單號和訂單信息發送給MQ
                            // 秒殺成功 快速下單 發送消息到 MQ 整個操作時間在 10ms 左右
                            String timeId = IdWorker.getTimeId();
                            SeckillOrderTo orderTo = new SeckillOrderTo();
                            orderTo.setOrderSn(timeId);
                            orderTo.setMemberId(user.getId());
                            orderTo.setNum(num);
                            orderTo.setPromotionSessionId(redisTo.getPromotionSessionId());
                            orderTo.setSkuId(redisTo.getSkuId());
                            orderTo.setSeckillPrice(redisTo.getSeckillPrice());
                            rabbitTemplate.convertAndSend("order-event-exchange", "order.seckill.order", orderTo);
                            long s2 = System.currentTimeMillis();
                            log.info("耗時..." + (s2 - s1));
                            return timeId;
                        }
                    }
                }
            }
        }
        long s3 = System.currentTimeMillis();
        log.info("耗時..." + (s3 - s1));
        return null;
    }

}
