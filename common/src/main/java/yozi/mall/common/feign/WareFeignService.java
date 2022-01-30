package yozi.mall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import yozi.mall.common.config.FeignConfig;
import yozi.mall.common.to.WareSkuLockTo;
import yozi.mall.common.utils.R;

import java.util.List;

@FeignClient(name = "ware", configuration = FeignConfig.class)
public interface WareFeignService {

    // 查詢sku是否有庫存 done
    @PostMapping("/ware/waresku/hasStock")
    R getSkuHasStock(@RequestBody List<Long> ids);

    /**
     * 查詢運費和收貨地址信息 done(暫時強制運費0)
     */
    @GetMapping(value = "/ware/wareinfo/fare")
    R getFare(@RequestParam("addrId") Long addrId);

    /**
     * 鎖定庫存 done
     */
    @PostMapping(value = "/ware/waresku/lock/order")
    R orderLockStock(@RequestBody WareSkuLockTo vo);
}
