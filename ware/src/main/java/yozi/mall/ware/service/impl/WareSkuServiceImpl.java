package yozi.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yozi.mall.common.feign.ProductFeignService;
import yozi.mall.common.to.SkuHasStockTo;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.common.utils.R;
import yozi.mall.ware.dao.WareSkuDao;
import yozi.mall.ware.entity.WareSkuEntity;
import yozi.mall.ware.service.WareSkuService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private WareSkuDao wareSkuDao;

    @Autowired
    private ProductFeignService productFeignService;

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

}