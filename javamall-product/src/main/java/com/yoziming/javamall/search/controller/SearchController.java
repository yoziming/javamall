package com.yoziming.javamall.search.controller;

import com.alibaba.fastjson.JSONObject;
import com.yoziming.common.to.CategoryVo;
import com.yoziming.javamall.product.service.CategoryService;
import com.yoziming.javamall.search.service.MallSearchService;
import com.yoziming.javamall.search.vo.SearchParam;
import com.yoziming.javamall.search.vo.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/1/30 15:35
 */
@Slf4j
@Controller
public class SearchController {

    @Autowired
    private MallSearchService mallSearchService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 自動將頁面提交過來的所有請求參數封裝成我們指定的對象
     *
     * @param param
     * @return
     */
    @GetMapping(value = "/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) {

        param.set_queryString(request.getQueryString());

        //1、根據傳遞來的頁面的查詢參數，去es中檢索商品
        SearchResult result = mallSearchService.search(param);
        List<CategoryVo> categoryVos = categoryService.getCategory();
        log.info("三級分類={}", JSONObject.toJSONString(categoryVos));
        model.addAttribute("category", categoryVos);
        model.addAttribute("result", result);

        return "searchlist";
    }

}
