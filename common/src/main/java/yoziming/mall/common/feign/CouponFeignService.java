package yoziming.mall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import yoziming.mall.common.to.SkuReductionTo;
import yoziming.mall.common.to.SpuBoundsTo;
import yoziming.mall.common.utils.R;

@FeignClient("coupon")
public interface CouponFeignService {
    @RequestMapping("/coupon/coupon/membercoupon")
    R memberCoupon();

    // 上傳商品時，保存 遠程調用的積分訊息 done
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundsTo spuBoundsTo);
    // 相當於 R save(@RequestBody SpuBoundsEntity spuBounds)
    // 因為都是轉成JSON在傳送，轉回來屬性能對上就行

    // 上傳商品時，保存 遠程調用的滿減、會員優惠訊息 done
    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);

    /**
     * 查詢最近三天需要參加秒殺商品的信息
     * done
     *
     * @return
     */
    @GetMapping(value = "/coupon/seckillsession/Lates3DaySession")
    R getLates3DaySession();
}
