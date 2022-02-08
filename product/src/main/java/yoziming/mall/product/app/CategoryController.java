package yoziming.mall.product.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.R;
import yoziming.mall.product.entity.CategoryEntity;
import yoziming.mall.product.service.CategoryService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品三級分類
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表，3級樹形
     */
    @RequestMapping("/list/tree")
    //@RequiresPermissions("product:category:list")
    public R listTree() {
        List<CategoryEntity> data = categoryService.listTree();

        return R.ok().put("data", data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:category:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateCascade(category);

        return R.ok();
    }

    /**
     * 修改排序
     */
    @RequestMapping("/update/sort")
    //@RequiresPermissions("product:category:update")
    public R updateSort(@RequestBody List<CategoryEntity> list) {
        categoryService.updateBatchById(list);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:category:delete")
    public R delete(@RequestBody Long[] catIds) {
        // categoryService.removeByIds(Arrays.asList(catIds));
        // 自訂檢查且批量刪除
        categoryService.removeMenuByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
