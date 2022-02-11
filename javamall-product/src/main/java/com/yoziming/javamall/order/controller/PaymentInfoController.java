package com.yoziming.javamall.order.controller;

import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.R;
import com.yoziming.javamall.order.entity.PaymentInfoEntity;
import com.yoziming.javamall.order.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 支付訊息表
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
@RestController
@RequestMapping("order/paymentinfo")
public class PaymentInfoController {
    @Autowired
    private PaymentInfoService paymentInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //    @RequiresPermissions("order:paymentinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = paymentInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 訊息
     */
    @RequestMapping("/info/{id}")
    //    @RequiresPermissions("order:paymentinfo:info")
    public R info(@PathVariable("id") Long id) {
        PaymentInfoEntity paymentInfo = paymentInfoService.getById(id);

        return R.ok().put("paymentInfo", paymentInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //    @RequiresPermissions("order:paymentinfo:save")
    public R save(@RequestBody PaymentInfoEntity paymentInfo) {
        paymentInfoService.save(paymentInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //    @RequiresPermissions("order:paymentinfo:update")
    public R update(@RequestBody PaymentInfoEntity paymentInfo) {
        paymentInfoService.updateById(paymentInfo);

        return R.ok();
    }

    /**
     * 刪除
     */
    @RequestMapping("/delete")
    //    @RequiresPermissions("order:paymentinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        paymentInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
