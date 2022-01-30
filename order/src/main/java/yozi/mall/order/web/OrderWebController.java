package yozi.mall.order.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yozi.mall.common.exception.NoStockException;
import yozi.mall.order.service.OrderService;
import yozi.mall.order.vo.OrderConfirmVo;
import yozi.mall.order.vo.OrderSubmitVo;
import yozi.mall.order.vo.SubmitOrderResponseVo;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

@Controller
public class OrderWebController {

    @Autowired
    private OrderService orderService;

    // 去結算確認頁
    @GetMapping(value = "/toTrade")
    public String toTrade(Model model, HttpServletRequest request) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("confirmOrderData", confirmVo);
        // 展示訂單確認的數據
        return "confirm";
    }

    // 下單
    @PostMapping(value = "/submitOrder")
    public String submitOrder(OrderSubmitVo vo, Model model, RedirectAttributes attributes) {

        try {
            SubmitOrderResponseVo responseVo = orderService.submitOrder(vo);
            //下單成功來到支付選擇頁
            //下單失敗回到訂單確認頁重新確定訂單信息
            if (responseVo.getCode() == 0) {
                //成功
                model.addAttribute("submitOrderResp", responseVo);
                return "pay";
            } else {
                String msg = "下單失敗";
                switch (responseVo.getCode()) {
                    case 1:
                        msg += "令牌訂單信息過期，請刷新再次提交";
                        break;
                    case 2:
                        msg += "訂單商品價格發生變化，請確認后再次提交";
                        break;
                    case 3:
                        msg += "庫存鎖定失敗，商品庫存不足";
                        break;
                }
                attributes.addFlashAttribute("msg", msg);
                return "redirect:http://order.mall.com/toTrade";
            }
        } catch (Exception e) {
            if (e instanceof NoStockException) {
                String message = ((NoStockException) e).getMessage();
                attributes.addFlashAttribute("msg", message);
            }
            return "redirect:http://order.mall.com/toTrade";
        }
    }

}
