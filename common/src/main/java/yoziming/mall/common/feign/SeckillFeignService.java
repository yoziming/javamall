package yoziming.mall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yoziming.mall.common.feign.fallback.SeckillFeignServiceFallBack;
import yoziming.mall.common.utils.R;

@FeignClient(value = "seckill", fallback = SeckillFeignServiceFallBack.class)
public interface SeckillFeignService {

    /**
     * 根據skuId查詢商品是否參加秒殺活動
     */
    @GetMapping(value = "/sku/seckill/{skuId}")
    R getSkuSeckilInfo(@PathVariable("skuId") Long skuId);

}
