package yozi.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.product.entity.CategoryBrandRelationEntity;

import java.util.Map;

/**
 * 品牌分類關聯
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

