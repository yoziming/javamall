package yoziming.mall.seckill.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yoziming.mall.common.to.SeckillSkuRedisTo;
import yoziming.mall.common.utils.R;
import yoziming.mall.seckill.service.SeckillService;

import java.util.List;

@Slf4j
@Controller
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 當前時間可以參與秒殺的商品信息
     */
    @GetMapping(value = "/getCurrentSeckillSkus")
    @ResponseBody
    public R getCurrentSeckillSkus() {
        // 測試sentinel
        log.info("sentinel");
        // 獲取到當前可以參加秒殺商品的信息
        List<SeckillSkuRedisTo> vos = seckillService.getCurrentSeckillSkus();
        return R.ok().setData(vos);
    }

    /**
     * 根據skuId查詢商品是否參加秒殺活動
     */
    @GetMapping(value = "/sku/seckill/{skuId}")
    @ResponseBody
    public R getSkuSeckilInfo(@PathVariable("skuId") Long skuId) {
        SeckillSkuRedisTo to = seckillService.getSkuSeckilInfo(skuId);
        return R.ok().setData(to);
    }

    /**
     * 商品進行秒殺(秒殺開始)
     */
    @GetMapping(value = "/kill")
    public String seckill(@RequestParam("killId") String killId,
                          @RequestParam("key") String key,
                          @RequestParam("num") Integer num,
                          Model model) {

        String orderSn = null;
        try {
            //1、判斷是否登錄
            orderSn = seckillService.kill(killId, key, num);
            model.addAttribute("orderSn", orderSn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

}
