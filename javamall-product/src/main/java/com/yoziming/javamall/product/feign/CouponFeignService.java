package com.yoziming.javamall.product.feign;

import com.yoziming.common.to.SkuReductionTo;
import com.yoziming.common.to.SpuBoundTo;
import com.yoziming.common.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: yoziming
 * @Date: 2022/1/15 14:54
 * @Description:
 */
//@Component
//@FeignClient("javamall-coupon")
public interface CouponFeignService {

    /**
     * 1、CouponFeignService.saveSpuBounds(spuBoundTo);
     * 1）、@RequestBody將這個對象轉為json。
     * 2）、找到gulimall-coupon服務，給/coupon/spubounds/save發送請求。
     * 將上一步轉的json放在請求體位置，發送請求；
     * 3）、對方服務收到請求。請求體里有json數據。
     * (@RequestBody SpuBoundsEntity spuBounds)；將請求體的json轉為SpuBoundsEntity；
     * 只要json數據模型是兼容的。雙方服務無需使用同一個to
     *
     * @param spuBoundTo
     * @return
     */
    @PostMapping("coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
