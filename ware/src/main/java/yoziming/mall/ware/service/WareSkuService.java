package yoziming.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.to.OrderTo;
import yoziming.mall.common.to.SkuHasStockTo;
import yoziming.mall.common.to.mq.StockLockedTo;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.ware.entity.WareSkuEntity;
import yoziming.mall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品庫存
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:44:25
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockTo> getSkuHasStock(List<Long> ids);

    boolean orderLockStock(WareSkuLockVo vo);

    /**
     * 解鎖庫存
     */
    void unlockStock(StockLockedTo to);

    /**
     * 解鎖訂單
     */
    void unlockStock(OrderTo orderTo);
}

