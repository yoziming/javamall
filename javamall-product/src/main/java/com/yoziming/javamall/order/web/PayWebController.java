package com.yoziming.javamall.order.web;

import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.yoziming.common.to.CategoryVo;
import com.yoziming.common.utils.R;
import com.yoziming.javamall.config.AlipayTemplate;
import com.yoziming.javamall.order.service.OrderService;
import com.yoziming.javamall.order.vo.PayAsyncVo;
import com.yoziming.javamall.order.vo.PayVo;
import com.yoziming.javamall.product.controller.CategoryController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: yoziming
 * @Date: 2022/3/2 16:54
 * @Description:
 */
@Controller
@Slf4j
public class PayWebController {

    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    OrderService orderService;

    @Autowired
    CategoryController categoryController;

    /**
     * 將支付頁讓瀏覽器展示，
     * 支付成功以後，要跳到用戶的訂單列表頁
     *
     * @param orderSn
     * @return
     * @throws AlipayApiException
     */
    @ResponseBody
    @GetMapping(value = "/payOrder", produces = "text/html")
    public String payOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {

        PayVo payVo = orderService.getOrderPay(orderSn);
        String pay = alipayTemplate.pay(payVo);
        System.out.println(pay);
        return pay;
    }

    /**
     * 支付寶支付成功的通知接口
     *
     * @param vo
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @ResponseBody
    @PostMapping("/payed/notify")
    public String handelAlipayed(PayAsyncVo vo, HttpServletRequest request) throws UnsupportedEncodingException,
            AlipayApiException {
        //只要我們收到了支付寶給我們異步的通知，告訴我們訂單支付成功。返回success,支付寶不在通知
        //驗簽
        log.info("支付寶異步通知接口開始：req = ", vo);
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //亂碼解決，這段代碼在出現亂碼時使用
            //            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //調用SDK驗證簽名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipay_public_key(),
                alipayTemplate.getCharset(), alipayTemplate.getSign_type());
        if (signVerified) {
            //簽名驗證成功
            log.info("簽名驗證成功...");
            String result = orderService.handlePayResult(vo);
            return result;
        } else {
            log.error("簽名驗證失敗...");
            return "error";
        }
    }

    @GetMapping("payed/result.html")
    public String paySuccess(HttpServletRequest request, Model model) {
        log.info("查詢支付結果：outTradeNo={}", request.getParameter("out_trade_no"));
        String s = orderService.handlePayResult(request.getParameter("out_trade_no"));
        if (s.equals("success")) {
            model.addAttribute("result", "支付成功！");
        } else {
            model.addAttribute("result", "支付失敗！");
        }
        R cat = categoryController.getCat();
        List<CategoryVo> category = cat.getData("category", new TypeReference<List<CategoryVo>>() {
        });
        model.addAttribute("category", category);
        return "orderpaysuccess";
    }

    //    @GetMapping("payed/refresh")
    //    public String payRefresh(HttpServletRequest request, Model model){
    //        log.info("查詢支付結果：outTradeNo={}", request.getParameter("out_trade_no"));
    //        String s = orderService.handlePayResult(request.getParameter("out_trade_no"));
    //        if (s.equals("success")){
    //            model.addAttribute("result", "支付成功！");
    //        }else {
    //            model.addAttribute("result", "支付失敗！");
    //        }
    //        R cat = categoryController.getCat();
    //        List<CategoryVo> category = cat.getData("category", new TypeReference<List<CategoryVo>>() {
    //        });
    //        model.addAttribute("category",category);
    //        return "orderpaysuccess";
    //    }

}
