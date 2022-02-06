package yozi.mall.seckill.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import yozi.mall.seckill.service.SeckillService;

import java.util.concurrent.TimeUnit;

/**
 * 秒殺商品定時上架
 * 每天3點，上架最近三天需要三天秒殺的商品
 * 當天00:00:00 - 23:59:59
 * 明天00:00:00 - 23:59:59
 * 後天00:00:00 - 23:59:59
 */

@Slf4j
@Service
public class SeckillScheduled {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedissonClient redissonClient;

    // 秒殺商品上架功能的鎖
    private final String upload_lock = "seckill:upload:lock";

    @Scheduled(cron = "*/5 * * * * ? ")
    // @Scheduled(cron = "0 0 3 * * ? ")
    public void uploadSeckillSkuLatest3Days() {
        // 重複上架先不處理
        log.info("上架秒殺的商品...");

        // 分佈式鎖
        RLock lock = redissonClient.getLock(upload_lock);
        try {
            // 加鎖
            lock.lock(10, TimeUnit.SECONDS);
            seckillService.uploadSeckillSkuLatest3Days();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
