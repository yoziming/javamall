package yozi.mall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yozi.mall.common.exception.NoStockException;
import yozi.mall.common.feign.OrderFeignService;
import yozi.mall.common.feign.ProductFeignService;
import yozi.mall.common.to.OrderTo;
import yozi.mall.common.to.SkuHasStockTo;
import yozi.mall.common.to.mq.StockDetailTo;
import yozi.mall.common.to.mq.StockLockedTo;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.common.utils.R;
import yozi.mall.ware.dao.WareSkuDao;
import yozi.mall.ware.entity.WareOrderTaskDetailEntity;
import yozi.mall.ware.entity.WareOrderTaskEntity;
import yozi.mall.ware.entity.WareSkuEntity;
import yozi.mall.ware.service.WareOrderTaskDetailService;
import yozi.mall.ware.service.WareOrderTaskService;
import yozi.mall.ware.service.WareSkuService;
import yozi.mall.ware.vo.OrderItemVo;
import yozi.mall.ware.vo.OrderVo;
import yozi.mall.ware.vo.WareSkuLockVo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private WareSkuDao wareSkuDao;
    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private WareOrderTaskService wareOrderTaskService;
    @Autowired
    private WareOrderTaskDetailService wareOrderTaskDetailService;
    @Autowired
    private OrderFeignService orderFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {

        // 某倉庫中的某sku應該是唯一的
        WareSkuEntity isExistWareSku = wareSkuDao.selectOne(
                new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if (isExistWareSku == null) {
            // 判讀如果沒有這個庫存記錄，新增
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStockLocked(0);
            // 遠端查找setSkuName
            R info = productFeignService.info(skuId);
            Map<String, Object> data = (Map<String, Object>) info.get("skuInfo");
            if (info.getCode() == 0) {
                wareSkuEntity.setSkuName((String) data.get("skuName"));
            }
            // 添加庫存信息
            wareSkuDao.insert(wareSkuEntity);
        } else {
            // 修改庫存信息
            Integer oldStock = isExistWareSku.getStock();
            wareSkuDao.addStock(skuId, wareId, oldStock + skuNum);
        }
    }

    @Override
    public List<SkuHasStockTo> getSkuHasStock(List<Long> ids) {
        List<SkuHasStockTo> collect = ids.stream().map(e -> {
            Long count = baseMapper.getStock(e);
            SkuHasStockTo to = new SkuHasStockTo();
            to.setSkuId(e);
            if (count != null) {
                to.setHasStock(count > 0);
            } else {
                to.setHasStock(false);
            }
            return to;
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 為某個訂單鎖定庫存
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean orderLockStock(WareSkuLockVo vo) {

        /**
         * 保存庫存工作單詳情信息
         * 追溯
         * 如果沒有庫存，就不會發送消息給mq
         * 【不會進入save(WareOrderTaskDetailEntity)邏輯，也不會發送消息給mq，也不會鎖定庫存，也不會監聽到解鎖服務】
         */
        WareOrderTaskEntity wareOrderTaskEntity = new WareOrderTaskEntity();
        wareOrderTaskEntity.setOrderSn(vo.getOrderSn());
        wareOrderTaskEntity.setCreateTime(new Date());
        wareOrderTaskService.save(wareOrderTaskEntity);

        //1、按照下單的收貨地址，找到一個就近倉庫，鎖定庫存
        //2、找到每個商品在哪個倉庫都有庫存
        List<OrderItemVo> locks = vo.getLocks();

        List<SkuWareHasStock> collect = locks.stream().map((item) -> {
            SkuWareHasStock stock = new SkuWareHasStock();
            Long skuId = item.getSkuId();
            stock.setSkuId(skuId);
            stock.setNum(item.getCount());
            //查詢這個商品在哪個倉庫有庫存 stock-鎖定num > 0
            List<Long> wareIdList = wareSkuDao.listWareIdHasSkuStock(skuId);
            stock.setWareId(wareIdList);

            return stock;
        }).collect(Collectors.toList());

        //2、鎖定庫存
        for (SkuWareHasStock hasStock : collect) {
            boolean skuStocked = false;
            Long skuId = hasStock.getSkuId();
            List<Long> wareIds = hasStock.getWareId();

            if (CollectionUtils.isEmpty(wareIds)) {
                //沒有任何倉庫有這個商品的庫存
                throw new NoStockException(skuId);
            }

            //1、如果每一個商品都鎖定成功,將當前商品鎖定了幾件的工作單記錄發給MQ
            //2、鎖定失敗。前面保存的工作單信息都回滾了。發送出去的消息，即使要解鎖庫存，由於在數據庫查不到指定的id，就不用解鎖
            for (Long wareId : wareIds) {
                //鎖定成功就返回1，失敗就返回0
                Long count = wareSkuDao.lockSkuStock(skuId, wareId, hasStock.getNum());
                // count==1表示鎖定成功
                if (count == 1) {
                    skuStocked = true;
                    WareOrderTaskDetailEntity taskDetailEntity = WareOrderTaskDetailEntity.builder()
                            .skuId(skuId)
                            .skuName("")
                            .skuNum(hasStock.getNum())
                            .taskId(wareOrderTaskEntity.getId())
                            .wareId(wareId)
                            .lockStatus(1)
                            .build();
                    wareOrderTaskDetailService.save(taskDetailEntity);

                    // 告訴MQ庫存鎖定成功
                    StockLockedTo lockedTo = new StockLockedTo();
                    lockedTo.setId(wareOrderTaskEntity.getId());
                    StockDetailTo detailTo = new StockDetailTo();
                    BeanUtils.copyProperties(taskDetailEntity, detailTo);// 這裏直接存entity。但是應該存id更好，數據最好來自DB
                    lockedTo.setDetailTo(detailTo);
                    rabbitTemplate.convertAndSend("stock-event-exchange", "stock.locked", lockedTo);
                    // 鎖定成功返回
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
        //3、肯定全部都是鎖定成功的
        return true;
    }

    /**
     * 解鎖庫存
     */
    @Override
    public void unlockStock(StockLockedTo to) {
        //庫存工作單的id
        StockDetailTo detail = to.getDetailTo();
        Long detailId = detail.getId();

        /**
         * 解鎖
         * 1、查詢數據庫關於這個訂單鎖定庫存信息
         *   有：證明庫存鎖定成功了
         *      解鎖：訂單狀況
         *          1、沒有這個訂單，必須解鎖庫存
         *          2、有這個訂單，不一定解鎖庫存
         *              訂單狀態：已取消：解鎖庫存
         *                      已支付：不能解鎖庫存
         */
        WareOrderTaskDetailEntity taskDetailInfo = wareOrderTaskDetailService.getById(detailId);
        if (taskDetailInfo != null) {
            //查出wms_ware_order_task工作單的信息
            Long id = to.getId();
            WareOrderTaskEntity orderTaskInfo = wareOrderTaskService.getById(id);
            //獲取訂單號查詢訂單狀態
            String orderSn = orderTaskInfo.getOrderSn();
            //遠程查詢訂單信息
            R orderData = orderFeignService.getOrderStatus(orderSn);
            if (orderData.getCode() == 0) {
                //訂單數據返回成功
                OrderVo orderInfo = orderData.getData("data", new TypeReference<OrderVo>() {
                });
                //判斷訂單狀態是否已取消或者支付或者訂單不存在
                // 1、訂單不存在：解鎖
                // 2、訂單存在，且訂單狀態是取消狀態：解鎖
                if (orderInfo == null || orderInfo.getStatus() == 4) {
                    // 工作單狀態必須是 已鎖定 才可以解鎖【因為解鎖方法沒有加事務】
                    if (taskDetailInfo.getLockStatus() == 1) {
                        unLockStock(detail.getSkuId(), detail.getWareId(), detail.getSkuNum(), detailId);
                    }
                }
            } else {
                //消息拒絕以後重新放在隊列裏面，讓別人繼續消費解鎖
                //遠程調用服務失敗
                throw new RuntimeException("遠程調用服務失敗");
            }
        } else {
            //無需解鎖【回滾狀態】
        }
    }

    /**
     * 防止訂單服務卡頓，導致訂單狀態消息一直改不了，庫存優先到期，查訂單狀態新建，什麼都不處理
     * 導致卡頓的訂單，永遠都不能解鎖庫存
     *
     * @param orderTo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unlockStock(OrderTo orderTo) {

        String orderSn = orderTo.getOrderSn();
        //查一下最新的庫存解鎖狀態，防止重複解鎖庫存
        WareOrderTaskEntity orderTaskEntity = wareOrderTaskService.getOrderTaskByOrderSn(orderSn);

        //按照工作單的id找到所有 沒有解鎖的庫存，進行解鎖
        Long id = orderTaskEntity.getId();
        List<WareOrderTaskDetailEntity> list =
                wareOrderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetailEntity>()
                        .eq("task_id", id).eq("lock_status", 1));

        for (WareOrderTaskDetailEntity taskDetailEntity : list) {
            unLockStock(taskDetailEntity.getSkuId(),
                    taskDetailEntity.getWareId(),
                    taskDetailEntity.getSkuNum(),
                    taskDetailEntity.getId());
        }
    }

    /**
     * 解鎖庫存的方法【設計DB，沒加事務】
     */
    public void unLockStock(Long skuId, Long wareId, Integer num, Long taskDetailId) {
        // 1、庫存解鎖
        wareSkuDao.unLockStock(skuId, wareId, num);

        // 2、更新工作單的狀態 為已解鎖 2
        WareOrderTaskDetailEntity taskDetailEntity = new WareOrderTaskDetailEntity();
        taskDetailEntity.setId(taskDetailId);
        taskDetailEntity.setLockStatus(2);
        wareOrderTaskDetailService.updateById(taskDetailEntity);
    }

    @Data
    class SkuWareHasStock {
        private Long skuId;
        private Integer num;
        private List<Long> wareId;
    }
}