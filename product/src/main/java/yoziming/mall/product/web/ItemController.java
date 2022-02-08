package yoziming.mall.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yoziming.mall.product.service.SkuInfoService;
import yoziming.mall.product.vo.SkuItemVo;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@Controller
public class ItemController {

    @Resource
    private SkuInfoService skuInfoService;

    /**
     * 展示當前sku的詳情
     *
     * @param skuId
     * @return
     */
    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) throws ExecutionException,
            InterruptedException {
        System.out.println("準備查詢" + skuId + "詳情");

        /**
         * 1、sku基本信息【標題、副標題、價格】pms_sku_info
         * 2、sku圖片信息【每個sku_id對應了多個圖片】pms_sku_images
         * 3、spu下所有sku銷售屬性組合【不只是當前sku_id所指定的商品】
         * 4、spu商品介紹【】
         * 5、spu規格與包裝【參數信息】
         */
        SkuItemVo vos = skuInfoService.item(skuId);
        model.addAttribute("item", vos);
        return "item";
    }
}
