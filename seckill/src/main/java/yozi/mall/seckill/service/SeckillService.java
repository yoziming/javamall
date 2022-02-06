package yozi.mall.seckill.service;

import yozi.mall.common.to.SeckillSkuRedisTo;

import java.util.List;

public interface SeckillService {

    /**
     * 上架三天需要秒殺的商品
     */
    void uploadSeckillSkuLatest3Days();

    List<SeckillSkuRedisTo> getCurrentSeckillSkus();

    /**
     * 根據skuId查詢商品是否參加秒殺活動
     *
     * @param skuId
     * @return
     */
    SeckillSkuRedisTo getSkuSeckilInfo(Long skuId);

    /**
     * 當前商品進行秒殺（秒殺開始）
     *
     * @param killId
     * @param key
     * @param num
     * @return
     */
    String kill(String killId, String key, Integer num) throws InterruptedException;
}
