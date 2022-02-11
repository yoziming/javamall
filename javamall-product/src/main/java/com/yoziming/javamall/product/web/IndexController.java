package com.yoziming.javamall.product.web;

import com.alibaba.fastjson.JSONObject;
import com.yoziming.common.to.CategoryVo;
import com.yoziming.javamall.product.entity.HomeAdvEntity;
import com.yoziming.javamall.product.service.CategoryService;
import com.yoziming.javamall.product.service.HomeAdvService;
import com.yoziming.javamall.product.vo.Catelog2Vo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author: yoziming
 * @Date: 2022/1/28 17:05
 * @Description:
 */
@Slf4j
@Controller
public class IndexController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private HomeAdvService homeAdvService;

    @GetMapping(value = "/index/catalog.json")
    @ResponseBody
    public Map<String, List<Catelog2Vo>> getCatalogJson() {

        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();
        log.info("分類Json={}", JSONObject.toJSONString(catalogJson));
        return catalogJson;
    }

    @GetMapping(value = {"/", "index.html"})
    public String getPageTest2(Model model) {
        List<CategoryVo> categoryVos = categoryService.getCategory();
        log.info("三級分類={}", JSONObject.toJSONString(categoryVos));
        model.addAttribute("category", categoryVos);
        //2、首頁運營位
        List<List<HomeAdvEntity>> indexAdv = homeAdvService.getIndexAdv();
        model.addAttribute("indexAdv", indexAdv);
        log.info("首頁運營位={}", JSONObject.toJSONString(indexAdv));
        return "index";
    }
}
