package yozi.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.to.SpuInfoTo;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.product.entity.SpuInfoEntity;
import yozi.mall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author yozi
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

