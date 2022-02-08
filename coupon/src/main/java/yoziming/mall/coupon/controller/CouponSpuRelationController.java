package yoziming.mall.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.R;
import yoziming.mall.coupon.entity.CouponSpuRelationEntity;
import yoziming.mall.coupon.service.CouponSpuRelationService;

import java.util.Arrays;
import java.util.Map;

/**
 * 優惠券與產品關聯
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
@RestController
@RequestMapping("coupon/couponspurelation")
public class CouponSpuRelationController {
    @Autowired
    private CouponSpuRelationService couponSpuRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:couponspurelation:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponSpuRelationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:couponspurelation:info")
    public R info(@PathVariable("id") Long id) {
        CouponSpuRelationEntity couponSpuRelation = couponSpuRelationService.getById(id);

        return R.ok().put("couponSpuRelation", couponSpuRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:couponspurelation:save")
    public R save(@RequestBody CouponSpuRelationEntity couponSpuRelation) {
        couponSpuRelationService.save(couponSpuRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:couponspurelation:update")
    public R update(@RequestBody CouponSpuRelationEntity couponSpuRelation) {
        couponSpuRelationService.updateById(couponSpuRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:couponspurelation:delete")
    public R delete(@RequestBody Long[] ids) {
        couponSpuRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
