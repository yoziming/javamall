package com.yoziming.javamall.order.controller;

import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.R;
import com.yoziming.javamall.order.entity.OrderItemEntity;
import com.yoziming.javamall.order.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 訂單項訊息
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
@RestController
@RequestMapping("order/orderitem")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //    @RequiresPermissions("order:orderitem:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderItemService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 訊息
     */
    @RequestMapping("/info/{id}")
    //    @RequiresPermissions("order:orderitem:info")
    public R info(@PathVariable("id") Long id) {
        OrderItemEntity orderItem = orderItemService.getById(id);

        return R.ok().put("orderItem", orderItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //    @RequiresPermissions("order:orderitem:save")
    public R save(@RequestBody OrderItemEntity orderItem) {
        orderItemService.save(orderItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //    @RequiresPermissions("order:orderitem:update")
    public R update(@RequestBody OrderItemEntity orderItem) {
        orderItemService.updateById(orderItem);

        return R.ok();
    }

    /**
     * 刪除
     */
    @RequestMapping("/delete")
    //    @RequiresPermissions("order:orderitem:delete")
    public R delete(@RequestBody Long[] ids) {
        orderItemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
