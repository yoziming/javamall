// package yoziming.mall.order.web;
//
//
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;
// import yoziming.mall.common.vo.orderVo.PayVo;
// import yoziming.mall.order.entity.OrderEntity;
// import yoziming.mall.order.service.OrderService;
//
// import javax.annotation.Resource;
//
//
//
// @Slf4j
// @Controller
// public class PayWebController {
//
//     @Autowired
//     private AlipayTemplate alipayTemplate;
//
//     @Autowired
//     private OrderService orderService;
//
//     @Autowired
//     private BestPayService bestPayService;
//
//     @Resource
//     private WxPayConfig wxPayConfig;
//
//     /**
//      * 用戶下單:支付寶支付
//      * 1、讓支付頁讓瀏覽器展示
//      * 2、支付成功以後，跳轉到用戶的訂單列表頁
//      * @param orderSn
//      * @return
//      * @throws AlipayApiException
//      */
//     @ResponseBody
//     @GetMapping(value = "/aliPayOrder",produces = "text/html")
//     public String aliPayOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
//
//         PayVo payVo = orderService.getOrderPay(orderSn);
//         // 支付寶返回一個頁面【支付寶賬戶登入的html頁面】
//         String pay = alipayTemplate.pay(payVo);
//         System.out.println(pay);
//         return pay;
//     }
//
//
//     /**
//      * 微信支付
//      * @param orderSn
//      * @return
//      */
//     @GetMapping(value = "/weixinPayOrder")
//     public String weixinPayOrder(@RequestParam("orderSn") String orderSn, Model model) {
//
//         OrderEntity orderInfo = orderService.getOrderByOrderSn(orderSn);
//
//         if (orderInfo == null) {
//             throw new RuntimeException("訂單不存在");
//         }
//
//         PayRequest request = new PayRequest();
//         request.setOrderName("4559066-最好的支付sdk");
//         request.setOrderId(orderInfo.getOrderSn());
//         request.setOrderAmount(0.01);
//         request.setPayTypeEnum(WXPAY_NATIVE);
//
//         PayResponse payResponse = bestPayService.pay(request);
//         payResponse.setOrderId(orderInfo.getOrderSn());
//         log.info("發起支付 response={}", payResponse);
//
//         //傳入前台的二維碼路徑生成支付二維碼
//         model.addAttribute("codeUrl",payResponse.getCodeUrl());
//         model.addAttribute("orderId",payResponse.getOrderId());
//         model.addAttribute("returnUrl",wxPayConfig.getReturnUrl());
//
//         return "createForWxNative";
//     }
//
//
//     //根據訂單號查詢訂單狀態的API
//     @GetMapping(value = "/queryByOrderId")
//     @ResponseBody
//     public OrderEntity queryByOrderId(@RequestParam("orderId") String orderId) {
//         log.info("查詢支付記錄...");
//         return orderService.getOrderByOrderSn(orderId);
//     }
//
//
//
// }
