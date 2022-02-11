package com.yoziming.javamall.product.feign;


import com.yoziming.common.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/1/15 14:54
 * @Description:
 */

//@FeignClient("javamall-ware")
public interface WareFeignService {

    @PostMapping(value = "/ware/waresku/hasStock")
    R getSkuHasStock(@RequestBody List<Long> skuIds);

}
