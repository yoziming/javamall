package yoziming.mall.member.web;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yoziming.mall.common.feign.OrderFeignService;
import yoziming.mall.common.utils.R;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberWebController {

    @Autowired
    private OrderFeignService orderFeignService;

    @GetMapping(value = "/memberOrder.html")
    public String memberOrderPage(@RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                  Model model) {

        //查出當前登錄用戶的所有訂單列表數據
        Map<String, Object> page = new HashMap<>();
        page.put("page", pageNum.toString());

        //遠程查詢訂單服務訂單數據
        R orderInfo = orderFeignService.listWithItem(page);
        System.out.println(JSON.toJSONString(orderInfo));
        model.addAttribute("orders", orderInfo);

        return "orderList";
    }
}
