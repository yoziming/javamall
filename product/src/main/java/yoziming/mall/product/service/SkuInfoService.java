package yoziming.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.product.entity.SkuInfoEntity;
import yoziming.mall.product.vo.SkuItemVo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * sku信息
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByKeyword(Map<String, Object> params);

    List<SkuInfoEntity> getSkusBySpuId(Long spuId);

    SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException;
}

