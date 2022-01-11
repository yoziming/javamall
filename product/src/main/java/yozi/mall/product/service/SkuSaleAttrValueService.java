package yozi.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.product.entity.SkuSaleAttrValueEntity;

import java.util.Map;

/**
 * sku銷售屬性&值
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

