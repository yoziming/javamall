package com.yoziming.javamall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.to.mq.OrderTo;
import com.yoziming.common.to.mq.StockLockedTo;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.ware.entity.WareSkuEntity;
import com.yoziming.javamall.ware.vo.SkuHasStockVo;
import com.yoziming.javamall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品庫存
 *
 * @author yoziming
 * @date 2022-01-20 17:41:43
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 添加庫存
     *
     * @param skuId
     * @param wareId
     * @param skuNum
     */
    void addStock(Long skuId, Long wareId, Integer skuNum);

    /**
     * 判斷是否有庫存
     *
     * @param skuIds
     * @return
     */
    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    /**
     * 鎖定庫存
     *
     * @param vo
     * @return
     */
    boolean orderLockStock(WareSkuLockVo vo);

    /**
     * 解鎖庫存
     *
     * @param to
     */
    void unlockStock(StockLockedTo to);

    /**
     * 解鎖訂單
     *
     * @param orderTo
     */
    void unlockStock(OrderTo orderTo);

    /**
     * 添加庫存
     *
     * @param wareSku
     */
    void saveWare(WareSkuEntity wareSku);

    /**
     * 確認收貨 減少庫存
     *
     * @param orderTo
     */
    void minusWare(OrderTo orderTo);
}

