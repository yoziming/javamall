package yoziming.mall.order.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import yoziming.mall.order.service.OrderService;

/**
 * 訂單支付成功監聽器
 */
@RestController
public class OrderPayedListener {

    @Autowired
    private OrderService orderService;

    // @Autowired
    // private AlipayTemplate alipayTemplate;
    //
    // @PostMapping(value = "/payed/notify")
    // public String handleAlipayed(PayAsyncVo asyncVo, HttpServletRequest request) throws AlipayApiException,
    // UnsupportedEncodingException {
    //     // 只要收到支付寶的異步通知，返回 success 支付寶便不再通知
    //     // 獲取支付寶POST過來反饋信息
    //     //TODO 需要驗簽
    //     Map<String, String> params = new HashMap<>();
    //     Map<String, String[]> requestParams = request.getParameterMap();
    //     for (String name : requestParams.keySet()) {
    //         String[] values = requestParams.get(name);
    //         String valueStr = "";
    //         for (int i = 0; i < values.length; i++) {
    //             valueStr = (i == values.length - 1) ? valueStr + values[i]
    //                     : valueStr + values[i] + ",";
    //         }
    //         //亂碼解決，這段代碼在出現亂碼時使用
    //         // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
    //         params.put(name, valueStr);
    //     }
    //
    //     boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipay_public_key(),
    //             alipayTemplate.getCharset(), alipayTemplate.getSign_type()); //調用SDK驗證簽名
    //
    //     if (signVerified) {
    //         System.out.println("簽名驗證成功...");
    //         //去修改訂單狀態
    //         String result = orderService.handlePayResult(asyncVo);
    //         return result;
    //     } else {
    //         System.out.println("簽名驗證失敗...");
    //         return "error";
    //     }
    // }
    //
    // @PostMapping(value = "/pay/notify")
    // public String asyncNotify(@RequestBody String notifyData) {
    //     //異步通知結果
    //     return orderService.asyncNotify(notifyData);
    // }

}
