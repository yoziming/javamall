package com.yoziming.javamall.product.controller;

import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.R;
import com.yoziming.javamall.product.entity.HomeAdvEntity;
import com.yoziming.javamall.product.service.HomeAdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 首頁輪播廣告
 *
 * @author yoziming
 * @date 2022-01-01 17:25:51
 */
@RestController
@RequestMapping("product/homeadv")
public class HomeAdvController {
    @Autowired
    private HomeAdvService homeAdvService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = homeAdvService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 訊息
     */
    @RequestMapping("/info/{id}")
    //    @RequiresPermissions("product:homeadv:info")
    public R info(@PathVariable("id") Long id) {
        HomeAdvEntity homeAdv = homeAdvService.getById(id);

        return R.ok().put("homeAdv", homeAdv);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //    @RequiresPermissions("product:homeadv:save")
    public R save(@RequestBody HomeAdvEntity homeAdv) {
        homeAdvService.save(homeAdv);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //    @RequiresPermissions("product:homeadv:update")
    public R update(@RequestBody HomeAdvEntity homeAdv) {
        homeAdvService.updateById(homeAdv);

        return R.ok();
    }

    /**
     * 刪除
     */
    @RequestMapping("/delete")
    //    @RequiresPermissions("product:homeadv:delete")
    public R delete(@RequestBody Long[] ids) {
        homeAdvService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
