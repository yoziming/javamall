package com.yoziming.javamall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.exception.NoStockException;
import com.yoziming.common.to.mq.OrderTo;
import com.yoziming.common.to.mq.StockDetailTo;
import com.yoziming.common.to.mq.StockLockedTo;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;
import com.yoziming.common.utils.R;
import com.yoziming.javamall.order.entity.OrderEntity;
import com.yoziming.javamall.order.service.OrderService;
import com.yoziming.javamall.product.controller.SkuInfoController;
import com.yoziming.javamall.ware.dao.WareSkuDao;
import com.yoziming.javamall.ware.entity.WareOrderTaskDetailEntity;
import com.yoziming.javamall.ware.entity.WareOrderTaskEntity;
import com.yoziming.javamall.ware.entity.WareSkuEntity;
import com.yoziming.javamall.ware.service.WareOrderTaskDetailService;
import com.yoziming.javamall.ware.service.WareOrderTaskService;
import com.yoziming.javamall.ware.service.WareSkuService;
import com.yoziming.javamall.ware.vo.SkuHasStockVo;
import com.yoziming.javamall.ware.vo.SkuInfoVo;
import com.yoziming.javamall.ware.vo.WareSkuLockVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service("wareSkuService")
@RabbitListener(queues = "stock.release.stock.queue")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private WareSkuDao wareSkuDao;

    @Autowired
    private SkuInfoController skuInfoController;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private WareOrderTaskService wareOrderTaskService;

    @Autowired
    private WareOrderTaskDetailService wareOrderTaskDetailService;

    @Autowired
    OrderService orderService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();

        String skuId = (String) params.get("skuId");
        if (!StringUtils.isEmpty(skuId) && !"0".equalsIgnoreCase(skuId)) {
            queryWrapper.eq("sku_id", skuId);
        }

        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId) && !"0".equalsIgnoreCase(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {

        //1、判讀如果沒有這個庫存記錄新增
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(
                new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));

        if (wareSkuEntities == null || wareSkuEntities.size() == 0) {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStockLocked(0);
            //遠程查詢sku的名字，如果失敗整個事務無需回滾
            //1、自己catch異常
            try {
                R info = skuInfoController.info(skuId);
                Map<String, Object> data = (Map<String, Object>) info.get("skuInfo");
                if (info.getCode() == 0) {
                    wareSkuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e) {

            }
            //添加庫存訊息
            wareSkuDao.insert(wareSkuEntity);
        } else {
            //修改庫存訊息
            wareSkuDao.addStock(skuId, wareId, skuNum);
        }

    }

    /**
     * 判斷是否有庫存
     *
     * @param skuIds
     * @return
     */
    @Override
    public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> skuHasStockVos = skuIds.stream().map(item -> {
            Long count = this.baseMapper.getSkuStock(item);
            SkuHasStockVo skuHasStockVo = new SkuHasStockVo();
            skuHasStockVo.setSkuId(item);
            skuHasStockVo.setHasStock(count == null ? false : count > 0);
            return skuHasStockVo;
        }).collect(Collectors.toList());
        return skuHasStockVos;
    }

    /**
     * 為某個訂單鎖定庫存
     * 預設只要是運行時異常都會回滾
     * <p>
     * 庫存解鎖的場景
     * 1、下訂單成功，訂單過期沒有支付被系統自動取消，被用戶手動取消，都要解鎖庫存
     * 2、下訂單成功，庫存鎖定成功，接下來的業務調用失敗，導致訂單回滾
     * 之前解鎖的庫存就要自動解鎖
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = NoStockException.class)
    @Override
    public boolean orderLockStock(WareSkuLockVo vo) {
        /**
         * 保存庫存工作單的詳情
         *
         */
        WareOrderTaskEntity taskEntity = new WareOrderTaskEntity();
        taskEntity.setOrderSn(vo.getOrderSn());
        wareOrderTaskService.save(taskEntity);

        //1、按照下單的收貨地址，找到一個就近倉庫，鎖定庫存
        //1、找到每個商品在哪個倉庫都有庫存
        List<com.yoziming.javamall.order.vo.OrderItemVo> locks = vo.getLocks();
        List<SkuWareHasStock> collect = locks.stream().map(item -> {
            SkuWareHasStock stock = new SkuWareHasStock();
            Long skuId = item.getSkuId();
            stock.setSkuId(skuId);
            stock.setNum(item.getCount());
            //查詢這個商品在哪裡有庫存
            List<Long> wareIds = wareSkuDao.listWareIdHasSkuStock(skuId);
            stock.setWareId(wareIds);
            return stock;
        }).collect(Collectors.toList());

        //2、鎖定庫存
        for (SkuWareHasStock hasStock : collect) {
            boolean skuStocked = false;
            Long skuId = hasStock.getSkuId();
            List<Long> wareIds = hasStock.getWareId();
            if (wareIds == null || wareIds.size() == 0) {
                //沒有任何倉庫有這個商品的庫存
                throw new NoStockException(skuId);
            }
            //1、如果每個商品都鎖定成功，將當前商品鎖定了幾件的工作單記錄發送給MQ
            //2、如果鎖定失敗，前面保存的工作的回滾，發送出去的消息，即使要解鎖記錄，由於去數據庫查不到id,所以就不用解鎖
            //
            for (Long wareId : wareIds) {
                //成功就返回1，否則為0
                Long count = wareSkuDao.lockSkuStock(skuId, wareId, hasStock.getNum());
                if (count == 1) {
                    skuStocked = true;
                    // 告訴MQ庫存鎖定成功
                    WareOrderTaskDetailEntity entity =
                            new WareOrderTaskDetailEntity(null, skuId, null, hasStock.getNum(), taskEntity.getId(),
                                    wareId, 1);
                    wareOrderTaskDetailService.save(entity);
                    StockLockedTo lockedTo = new StockLockedTo();
                    lockedTo.setId(taskEntity.getId());
                    StockDetailTo stockDetailTo = new StockDetailTo();
                    BeanUtils.copyProperties(entity, stockDetailTo);
                    //只發id不行，防止回滾以後找不到數據
                    lockedTo.setDetail(stockDetailTo);

                    rabbitTemplate.convertAndSend("stock-event-exchange", "stock.locked", lockedTo);
                    break;
                } else {
                    //當前倉庫鎖失敗，重試下一個倉庫
                }
            }
            if (skuStocked == false) {
                //當前商品所有倉庫都沒有鎖住
                throw new NoStockException(skuId);
            }
        }

        //3、肯定全部鎖定成功

        return true;
    }

    /**
     * 1、庫存自動解鎖
     * 下訂單成功，庫存鎖定成功，接下來的業務調用失敗，
     * 導致訂單回滾，之前解鎖的庫存就要自動解鎖
     * 2、訂單失敗
     * <p>
     * 只要解鎖庫存的消息失敗，一定要告訴服務解鎖失敗
     */
    @Override
    public void unlockStock(StockLockedTo to) {

        StockDetailTo detail = to.getDetail();
        Long detailId = detail.getId();
        //解鎖
        //1、查詢數據庫關於這個訂單的鎖定庫存訊息
        //有：證明庫存鎖定成功
        //    解鎖：訂單情況：1.沒有這個訂單，必須解鎖
        //                 2.有這個訂單。不是解鎖庫存。
        //                      訂單狀態：已取消：解鎖庫存
        //                              沒取消：不能解鎖
        //沒有：庫存鎖定失敗，庫存回滾了，這種情況無需解鎖
        WareOrderTaskDetailEntity byId = wareOrderTaskDetailService.getById(detailId);
        if (byId != null) {
            //解鎖
            Long id = to.getId();
            WareOrderTaskEntity taskEntity = wareOrderTaskService.getById(id);
            String orderSn = taskEntity.getOrderSn();
            OrderEntity orderByOrderSn = orderService.getOrderByOrderSn(orderSn);
            if (orderByOrderSn == null || orderByOrderSn.getStatus() == 4) {
                //訂單不存在，訂單已經被取消了，才能解鎖庫存
                //detailId
                if (byId.getLockStatus() == 1) {
                    //當前庫存工作單詳情，狀態1 已鎖定但是未解鎖才可以解鎖
                    unLockStock(detail.getSkuId(), detail.getWareId(), detail.getSkuNum(), detailId);
                }
            }
        } else {
            //無需解鎖
        }
    }

    //防止訂單服務卡頓，導致訂單狀態消息一直改不了，庫存消息優先到期，查訂單狀態新建狀態，
    //導致卡頓的訂單，永遠不能解鎖庫存
    @Transactional
    @Override
    public void unlockStock(OrderTo orderTo) {
        String orderSn = orderTo.getOrderSn();
        //查一下最新庫存的狀態，防止重複解鎖庫存
        WareOrderTaskEntity taskEntity = wareOrderTaskService.getOrderTaskByOrderSn(orderSn);
        if (Objects.nonNull(taskEntity)) {
            Long id = taskEntity.getId();
            //按照工作單找到所有沒有解鎖的庫存，進行解鎖
            List<WareOrderTaskDetailEntity> entities =
                    wareOrderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetailEntity>()
                    .eq("task_id", id)
                    .eq("lock_status", 1));
            for (WareOrderTaskDetailEntity entity : entities) {
                unLockStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum(), entity.getId());
            }
        }
    }

    @Override
    public void saveWare(WareSkuEntity wareSku) {
        wareSku.setWareId(1l);
        R info = skuInfoController.info(wareSku.getSkuId());
        log.info("遠程查詢sku訊息：r={}", info);
        SkuInfoVo skuInfo = info.getData("skuInfo", new TypeReference<SkuInfoVo>() {
        });
        if (Objects.isNull(skuInfo)) {
            // 為空 說明沒有這個sku
            return;
        }
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("sku_id", wareSku.getSkuId());
        WareSkuEntity wareSkuEntity = this.getOne(wrapper);
        if (Objects.isNull(wareSkuEntity)) {
            // 為空說明倉庫沒有這個商品的庫存
            wareSku.setSkuName(skuInfo.getSkuName());

            this.save(wareSku);
        } else {
            // 不為空直接在基礎上添加即可
            wareSkuEntity.setStock(wareSkuEntity.getStock() + wareSku.getStock());
            this.updateById(wareSkuEntity);
        }

    }

    @Override
    public void minusWare(OrderTo orderTo) {
        String orderSn = orderTo.getOrderSn();
        WareOrderTaskEntity wareOrderTaskEntity = wareOrderTaskService.getOrderTaskByOrderSn(orderSn);
        Long id = wareOrderTaskEntity.getId();
        //按照工作單找到所有鎖定的庫存
        List<WareOrderTaskDetailEntity> entities =
                wareOrderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetailEntity>()
                .eq("task_id", id)
                .eq("lock_status", 1));
        for (WareOrderTaskDetailEntity entity : entities) {
            unLockStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum(), entity.getId());
            Long skuId = entity.getSkuId();
            Integer skuNum = entity.getSkuNum();
            UpdateWrapper<WareSkuEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("sku_id", skuId)
                    .setSql("stock = stock - " + skuNum);
            this.update(updateWrapper);
        }
    }

    private void unLockStock(Long skuId, Long wareId, Integer num, Long taskDetailId) {
        //庫存解鎖
        wareSkuDao.unLockStock(skuId, wareId, num);
        //更新庫存工作單的狀態
        WareOrderTaskDetailEntity entity = new WareOrderTaskDetailEntity();
        entity.setId(taskDetailId);
        //變為已解鎖
        entity.setLockStatus(2);
        wareOrderTaskDetailService.updateById(entity);
    }

    @Data
    class SkuWareHasStock {
        private Long skuId;
        private Integer num;
        private List<Long> wareId;
    }
}