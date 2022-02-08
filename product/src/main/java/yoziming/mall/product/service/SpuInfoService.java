package yoziming.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.to.SpuInfoTo;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.product.entity.SpuInfoEntity;
import yoziming.mall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    PageUtils queryPageByKeyword(Map<String, Object> params);

    void up(Long spuId);

    SpuInfoTo getSpuInfoBySkuId(Long skuId);
}

