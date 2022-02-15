package yoziming.mall.order.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yoziming.mall.order.entity.OrderEntity;
import yoziming.mall.order.service.OrderService;

@Slf4j
@Controller
public class PayWebController {

    @Autowired
    private OrderService orderService;

    /**
     * 微信支付
     *
     * @param orderSn
     * @return
     */
    @GetMapping(value = "/weixinPayOrder")
    public String weixinPayOrder(@RequestParam("orderSn") String orderSn, Model model) {
        log.info("假裝支付...");

        OrderEntity orderInfo = orderService.getOrderByOrderSn(orderSn);

        if (orderInfo == null) {
            throw new RuntimeException("訂單不存在");
        }
        log.info("發起支付 response={}", orderInfo.getId());
        model.addAttribute("submitOrderResp", orderInfo);

        return "fake-pay-success";
    }

    //根據訂單號查詢訂單狀態的API
    @GetMapping(value = "/queryByOrderId")
    @ResponseBody
    public OrderEntity queryByOrderId(@RequestParam("orderId") String orderId) {
        log.info("查詢支付記錄...");
        return orderService.getOrderByOrderSn(orderId);
    }

}
