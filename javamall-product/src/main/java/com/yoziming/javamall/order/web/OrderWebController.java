package com.yoziming.javamall.order.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yoziming.common.to.CategoryVo;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.R;
import com.yoziming.common.vo.MemberRespVo;
import com.yoziming.javamall.interceptor.LoginUserInterceptor;
import com.yoziming.javamall.member.controller.MemberReceiveAddressController;
import com.yoziming.javamall.member.vo.AddressVo;
import com.yoziming.javamall.order.entity.OrderEntity;
import com.yoziming.javamall.order.entity.OrderReturnApplyEntity;
import com.yoziming.javamall.order.service.OrderReturnApplyService;
import com.yoziming.javamall.order.service.OrderService;
import com.yoziming.javamall.order.vo.OrderConfirmVo;
import com.yoziming.javamall.order.vo.OrderSubmitVo;
import com.yoziming.javamall.order.vo.SubmitOrderResponseVo;
import com.yoziming.javamall.product.controller.CategoryController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:27
 * @Description:
 */
@Slf4j
@Controller
public class OrderWebController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderReturnApplyService orderReturnApplyService;
    @Autowired
    MemberReceiveAddressController memberFeignService;
    @Autowired
    CategoryController categoryController;

    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = orderService.confirmOrder();
        //展示訂單確認的數據
        log.info("訂單確認頁={}", confirmVo.toString());
        model.addAttribute("orderConfirmData", confirmVo);
        R cat = categoryController.getCat();
        List<CategoryVo> category = cat.getData("category", new TypeReference<List<CategoryVo>>() {
        });
        model.addAttribute("category", category);
        return "orderconfirm";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo vo, Model model, RedirectAttributes attributes) {

        //下單：去創建訂單，驗令牌，驗價格，鎖庫存

        SubmitOrderResponseVo responseVo = orderService.submitOrder(vo);
        R cat = categoryController.getCat();
        List<CategoryVo> category = cat.getData("category", new TypeReference<List<CategoryVo>>() {
        });
        model.addAttribute("category", category);
        System.out.println("訂單提交的數據..." + vo);
        if (responseVo.getCode() == 0) {
            //下單成功來到支付選擇頁
            model.addAttribute("submitOrderResp", responseVo);
            return "orderpay";
        } else {
            String msg = "下單失敗:";
            switch (responseVo.getCode()) {
                case 1:
                    msg += "訂單訊息過期，請刷新再提交";
                    break;
                case 2:
                    msg += "訂單商品價格發生變化，請確認后再次提交";
                    break;
                case 3:
                    msg += "庫存鎖定失敗，商品庫存不足";
                    break;
            }
            //下單失敗回到訂單確認頁
            attributes.addFlashAttribute("msg", msg);
            return "redirect:http://localhost:11000/toTrade";
        }

    }

    @GetMapping("/cancelOrder/{orderSn}")
    public String closeOrder(@PathVariable String orderSn) {
        log.info("取消已支付未發貨訂單：orderSn={}", orderSn);
        orderService.cancelOrder(orderSn);
        return "redirect:http://localhost:11000/orderList.html";
    }

    /**
     * 確認收貨
     *
     * @param orderSn
     * @return
     */
    @GetMapping("/confirm/{orderSn}")
    public String confirmReceipt(@PathVariable String orderSn) {
        log.info("確認收貨訂單：orderSn={}", orderSn);
        orderService.confirmReceipt(orderSn);
        return "redirect:http://localhost:11000/orderList.html";
    }

    @GetMapping("/orderList.html")
    public String orderPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {
        PageUtils page1 = orderService.queryPageWithItem2(pageNum);
        List<Integer> pageNavs = new ArrayList<>();
        for (int i = 1; i <= page1.getTotalPage(); i++) {
            pageNavs.add(i);
        }

        log.info("調用訂單列表：{}", JSON.toJSONString(page1));
        R cat = categoryController.getCat();
        List<CategoryVo> category = cat.getData("category", new TypeReference<List<CategoryVo>>() {
        });
        model.addAttribute("category", category);
        model.addAttribute("pageNavs", pageNavs);
        model.addAttribute("orders", page1);
        return "orderList";
    }

    @GetMapping("/detail.html/{orderSn}")
    public String orderDetail(@PathVariable(value = "orderSn") String orderSn, Model model) {
        OrderEntity orderDetail = orderService.getOrderDetail(orderSn);
        if (orderDetail.getStatus() == 5 || orderDetail.getStatus() == 6) {
            // 售後中
            QueryWrapper<OrderReturnApplyEntity> returnWrqpper = new QueryWrapper<>();
            returnWrqpper.eq("order_sn", orderSn);
            OrderReturnApplyEntity returnApplyEntity = orderReturnApplyService.getOne(returnWrqpper);
            model.addAttribute("orderReturn", returnApplyEntity);
        }
        R cat = categoryController.getCat();
        List<CategoryVo> category = cat.getData("category", new TypeReference<List<CategoryVo>>() {
        });
        model.addAttribute("category", category);
        log.info("查詢訂單詳情頁：{}", JSONObject.toJSONString(orderDetail));
        model.addAttribute("order", orderDetail);
        return "orderdetail";
    }

    @PostMapping("/return")
    public String returnProduct(@RequestParam(value = "orderSn") String orderSn,
                                @RequestParam(value = "reason") String reason,
                                @RequestParam(value = "returnAmount") String returnAmount) {

        orderReturnApplyService.returnProduct(orderSn, reason, returnAmount);

        return "redirect:http://localhost:11000/detail.html/" + orderSn;
    }

    @PostMapping("/save/address")
    public String returnProduct(AddressVo addressVo) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        addressVo.setMemberId(memberRespVo.getId());

        memberFeignService.save(addressVo);

        return "redirect:http://localhost:11000/toTrade";
    }

    @GetMapping("/delete/address/{addressId}/{defaultStatus}")
    public String returnProduct(@PathVariable("addressId") Long addressId,
                                @PathVariable("defaultStatus") Integer defaultStatus) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        AddressVo addressVo = new AddressVo();
        addressVo.setId(addressId);
        addressVo.setDefaultStatus(defaultStatus);
        addressVo.setMemberId(memberRespVo.getId());
        memberFeignService.delete(addressVo);
        return "redirect:http://localhost:11000/toTrade";
    }

    @GetMapping("/defalt/address/{addressId}")
    public String setDefaultStatus(@PathVariable("addressId") Long addressId) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        AddressVo addressVo = new AddressVo();
        addressVo.setId(addressId);
        addressVo.setMemberId(memberRespVo.getId());
        memberFeignService.setDefault(addressVo);
        return "redirect:http://localhost:11000/toTrade";
    }

}
