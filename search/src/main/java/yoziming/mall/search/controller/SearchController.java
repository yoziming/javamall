package yoziming.mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import yoziming.mall.search.service.MallSearchService;
import yoziming.mall.search.vo.SearchParam;
import yoziming.mall.search.vo.SearchResult;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@Controller
public class SearchController {

    @Autowired
    private MallSearchService mallSearchService;

    @GetMapping(value = "/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) {
        // Spring自動將頁面提交過來的所有請求參數封裝成我們指定的SearchParam
        param.set_queryString(request.getQueryString());

        // 根據傳遞來的頁面的查詢參數，去es中檢索商品
        SearchResult result = mallSearchService.search(param);
        // 存到model
        model.addAttribute("result", result);
        return "list";
    }

}
