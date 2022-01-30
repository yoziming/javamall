package yozi.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.to.OrderTo;
import yozi.mall.common.to.SkuHasStockTo;
import yozi.mall.common.to.mq.StockLockedTo;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.ware.entity.WareSkuEntity;
import yozi.mall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品庫存
 *
 * @author yozi
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

