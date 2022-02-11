package com.yoziming.javamall.product.controller;

import com.yoziming.common.to.CategoryVo;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.R;
import com.yoziming.javamall.product.entity.CategoryEntity;
import com.yoziming.javamall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品三級分類
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCat")
    public R getCat() {
        List<CategoryVo> categoryVos = categoryService.getCategory();
        return R.ok().put("category", categoryVos);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/tree")
    //    @RequiresPermissions("product:category:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryService.queryPage(params);
        List<CategoryEntity> categoryEntities = categoryService.listWithTree();
        return R.ok().put("data", categoryEntities);
    }

    /**
     * 訊息
     */
    @RequestMapping("/info/{catId}")
    //    @RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //    @RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);

        return R.ok();
    }

    @RequestMapping("/update/sort")
    //@RequiresPermissions("product:category:update")
    public R updateSort(@RequestBody CategoryEntity[] category) {
        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //    @RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateCascade(category);

        return R.ok();
    }

    /**
     * 刪除
     */
    @RequestMapping("/delete")
    //    @RequiresPermissions("product:category:delete")
    public R delete(@RequestBody Long[] catIds) {
        categoryService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
