package yozi.mall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import yozi.mall.common.config.FeignConfig;
import yozi.mall.common.utils.R;

import java.util.Map;

@FeignClient(name = "order", configuration = FeignConfig.class)
public interface OrderFeignService {

    // 根據訂單編號查詢訂單狀態 done
    @GetMapping(value = "/order/order/status/{orderSn}")
    R getOrderStatus(@PathVariable("orderSn") String orderSn);

    // 會員查詢訂單
    @PostMapping("/order/order/listWithItem")
    R listWithItem(Map<String, Object> page);
}
