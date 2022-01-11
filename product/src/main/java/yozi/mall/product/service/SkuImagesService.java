package yozi.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.product.entity.SkuImagesEntity;

import java.util.Map;

/**
 * sku圖片
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

