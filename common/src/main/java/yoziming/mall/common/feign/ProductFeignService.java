package yoziming.mall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yoziming.mall.common.utils.R;

import java.math.BigDecimal;
import java.util.List;

@FeignClient("product")
public interface ProductFeignService {

    // 完成採購需求時，存表需要sku_name
    @RequestMapping("/product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);

    // 麵包屑導航
    @GetMapping("/product/attr/info/{attrId}")
    R attrInfo(@PathVariable("attrId") Long attrId);

    /**
     * 遠程調用的數據可以放入緩存中
     */
    @GetMapping("/product/brand/infos")
    public R brandsInfo(@RequestParam("brandIds") List<Long> brandIds);

    /**
     * 根據skuId查詢sku信息
     * done
     *
     * @param skuId
     * @return
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    R getInfo(@PathVariable("skuId") Long skuId);

    /**
     * 根據skuId查詢pms_sku_sale_attr_value表中的信息
     * done
     *
     * @param skuId
     * @return
     */
    @GetMapping(value = "/product/skusaleattrvalue/stringList/{skuId}")
    List<String> getSkuSaleAttrValues(@PathVariable("skuId") Long skuId);

    /**
     * 根據skuId查詢當前商品的最新價格
     * done
     *
     * @param skuId
     * @return
     */
    @GetMapping(value = "/product/skuinfo/{skuId}/price")
    BigDecimal getPrice(@PathVariable("skuId") Long skuId);

    /**
     * 根據skuId查詢spu的信息 done
     *
     * @param skuId
     * @return
     */
    @GetMapping(value = "/product/spuinfo/skuId/{skuId}")
    R getSpuInfoBySkuId(@PathVariable("skuId") Long skuId);
}
