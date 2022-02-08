package yoziming.mall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import yoziming.mall.common.config.FeignConfig;
import yoziming.mall.common.to.OrderItemTo;

import java.util.List;

@FeignClient(name = "cart", configuration = FeignConfig.class)
public interface CartFeignService {

    // 查詢當前用戶購物車選中的商品項，OrderItemTo是CartItemVo的超集 done
    @GetMapping(value = "/currentUserCartItems")
    List<OrderItemTo> getCurrentCartItems();

}
