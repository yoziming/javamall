package yozi.mall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import yozi.mall.common.to.SkuReductionTo;
import yozi.mall.common.to.SpuBoundsTo;
import yozi.mall.common.utils.R;

@FeignClient("coupon")
public interface CouponFeignService {
    @RequestMapping("/coupon/coupon/membercoupon")
    R memberCoupon();

    // 上傳商品時，保存 遠程調用的積分訊息
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundsTo spuBoundsTo);
    // 相當於 R save(@RequestBody SpuBoundsEntity spuBounds)
    // 因為都是轉成JSON在傳送，轉回來屬性能對上就行

    // 上傳商品時，保存 遠程調用的滿減、會員優惠訊息
    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
