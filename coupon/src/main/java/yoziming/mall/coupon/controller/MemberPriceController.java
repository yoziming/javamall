package yoziming.mall.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.R;
import yoziming.mall.coupon.entity.MemberPriceEntity;
import yoziming.mall.coupon.service.MemberPriceService;

import java.util.Arrays;
import java.util.Map;

/**
 * 商品會員價格
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
@RestController
@RequestMapping("coupon/memberprice")
public class MemberPriceController {
    @Autowired
    private MemberPriceService memberPriceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:memberprice:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberPriceService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:memberprice:info")
    public R info(@PathVariable("id") Long id) {
        MemberPriceEntity memberPrice = memberPriceService.getById(id);

        return R.ok().put("memberPrice", memberPrice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:memberprice:save")
    public R save(@RequestBody MemberPriceEntity memberPrice) {
        memberPriceService.save(memberPrice);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:memberprice:update")
    public R update(@RequestBody MemberPriceEntity memberPrice) {
        memberPriceService.updateById(memberPrice);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:memberprice:delete")
    public R delete(@RequestBody Long[] ids) {
        memberPriceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
