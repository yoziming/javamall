package com.yoziming.javamall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.to.CategoryVo;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.product.entity.CategoryEntity;
import com.yoziming.javamall.product.vo.Catalog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三級分類
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    /**
     * 找到catalogId的完整路徑
     * [父/子/孫]
     *
     * @param catalogId
     * @return
     */
    Long[] findCatalogPath(Long catalogId);

    void updateCascade(CategoryEntity category);

    List<CategoryEntity> getCategoryByIds(Long catIds);

    /**
     * 首頁獲取一級分類
     *
     * @return
     */
    List<CategoryEntity> getLevel1Categorys();

    /**
     * 首頁獲取分類二級json
     *
     * @return
     */
    Map<String, List<Catalog2Vo>> getCatalogJson();

    List<CategoryVo> getCategory();

}

