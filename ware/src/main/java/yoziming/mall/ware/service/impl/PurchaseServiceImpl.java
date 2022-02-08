package yoziming.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoziming.mall.common.constant.WareConstant;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.Query;
import yoziming.mall.ware.dao.PurchaseDao;
import yoziming.mall.ware.entity.PurchaseDetailEntity;
import yoziming.mall.ware.entity.PurchaseEntity;
import yoziming.mall.ware.service.PurchaseDetailService;
import yoziming.mall.ware.service.PurchaseService;
import yoziming.mall.ware.service.WareSkuService;
import yoziming.mall.ware.vo.MergeVo;
import yoziming.mall.ware.vo.PurchaseDoneVo;
import yoziming.mall.ware.vo.PurchaseItemDoneVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {
    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Autowired
    private WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    // 查詢未領取的採購單
    @Override
    public PageUtils queryPageUnreceive(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> queryWrapper = new QueryWrapper<PurchaseEntity>()
                .eq("status", 0).or().eq("status", 1);
        IPage<PurchaseEntity> page = this.page(new Query<PurchaseEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }

    // 合併採購需求
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        // 沒有選擇任何【採購單】，將自動創建新單進行合併。
        if (purchaseId == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            // 設置採購單的預設狀態
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            this.save(purchaseEntity);
            // 獲取新建採購單的id
            purchaseId = purchaseEntity.getId();
        }
        // 取出前端來的採購需求
        List<Long> items = mergeVo.getItems();

        // 確認採購單狀態是0,1才可以合併
        Collection<PurchaseDetailEntity> purchaseDetailEntities = purchaseDetailService.listByIds(items);
        purchaseDetailEntities.forEach((item) -> {
            if (!item.getStatus().equals(WareConstant.PurchaseDetailStatusEnum.CREATED.getCode())
                    && !item.getStatus().equals(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode())) {
                throw new IllegalArgumentException("正在採購，無法進行分配");
            }
        });
        // lambda表達式說要final變數
        Long finalPurchaseId = purchaseId;
        // 改變採購需求的狀態，併到這單上
        List<PurchaseDetailEntity> collect = items.stream().map(i -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(i);
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        //批量修改
        purchaseDetailService.updateBatchById(collect);

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseId);
        this.updateById(purchaseEntity);
    }

    /**
     * 領取採購單
     *
     * @param ids 採購單的id
     */
    @Override
    public void received(List<Long> ids) {

        List<PurchaseEntity> collect = ids.stream().map(this::getById)
                .filter(
                        // 1、確認當前採購單是新建或者是已分配狀態
                        item -> item.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() ||
                                item.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode())
                .peek(
                        // 改變採購單狀態為已領取
                        item -> {
                            item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
                        }).collect(Collectors.toList());
        // 保存
        this.updateBatchById(collect);

        // 改變採購需求的狀態
        collect.forEach((item) -> {
            // 用採購單號查其下的採購需求，且status<2(還沒去買)的
            QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<PurchaseDetailEntity>();
            wrapper.and(q -> {
                q.eq("purchase_id", item.getId());
                q.lt("status", 2);
            });
            List<PurchaseDetailEntity> list = purchaseDetailService.list(wrapper);

            List<PurchaseDetailEntity> detailEntities = list.stream().map(entity -> {
                // 更新要拿出id
                PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                purchaseDetailEntity.setId(entity.getId());
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
                return purchaseDetailEntity;
            }).collect(Collectors.toList());
            purchaseDetailService.updateBatchById(detailEntities);
        });
    }

    /**
     * 完成採購單
     *
     * @param doneVo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void done(PurchaseDoneVo doneVo) {

        // 採購單id
        Long id = doneVo.getId();
        // TODO 應該是要先判斷採購單與採購需求是否完成才能往下

        // 改變採購需求的狀態
        Boolean flag = true;
        List<PurchaseItemDoneVo> items = doneVo.getItems();
        List<PurchaseDetailEntity> updates = new ArrayList<>();
        for (PurchaseItemDoneVo item : items) {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            // 採購需求失敗
            if (item.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()) {
                flag = false;
                purchaseDetailEntity.setStatus(item.getStatus());
            } else {
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getCode());
                // 將成功採購的進行入庫
                // 查出當前採購項的詳細信息，入庫
                PurchaseDetailEntity entity = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum());
            }
            purchaseDetailEntity.setId(item.getItemId());
            // 裝進更新的list
            updates.add(purchaseDetailEntity);
        }
        // 批量更新
        purchaseDetailService.updateBatchById(updates);

        // 改變採購單狀態
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(id);
        purchaseEntity.setStatus(flag ? WareConstant.PurchaseStatusEnum.FINISH.getCode() :
                WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        this.updateById(purchaseEntity);
    }

}