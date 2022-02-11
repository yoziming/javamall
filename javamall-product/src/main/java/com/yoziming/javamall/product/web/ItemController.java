package com.yoziming.javamall.product.web;

import com.alibaba.fastjson.JSONObject;
import com.yoziming.common.to.CategoryVo;
import com.yoziming.javamall.product.service.CategoryService;
import com.yoziming.javamall.product.service.SkuInfoService;
import com.yoziming.javamall.product.vo.SkuItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author: yoziming
 * @Date: 2022/2/3 14:06
 * @Description:
 */
@Controller
@Slf4j
public class ItemController {

    @Autowired
    SkuInfoService skuInfoService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 展示當前sku的詳情
     *
     * @param skuId
     * @return
     */
    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable Long skuId, Model model) throws ExecutionException, InterruptedException {
        SkuItemVo vo = skuInfoService.item(skuId);
        List<CategoryVo> categoryVos = categoryService.getCategory();
        log.info("三級分類={}", JSONObject.toJSONString(categoryVos));
        model.addAttribute("category", categoryVos);
        model.addAttribute("item", vo);
        log.info("訪問sku頁面：item={}", JSONObject.toJSONString(vo));
        return "product";
    }

}

