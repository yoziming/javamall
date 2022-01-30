package yozi.mall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yozi.mall.common.utils.R;

@FeignClient("order")
public interface OrderFeignService {

    // 根據訂單編號查詢訂單狀態 done
    @GetMapping(value = "/order/order/status/{orderSn}")
    R getOrderStatus(@PathVariable("orderSn") String orderSn);
}
