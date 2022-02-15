package com.yoziming.javamall.cart.controller;

import com.alibaba.fastjson.TypeReference;
import com.yoziming.common.to.CategoryVo;
import com.yoziming.common.utils.R;
import com.yoziming.javamall.cart.service.CartService;
import com.yoziming.javamall.cart.vo.Cart;
import com.yoziming.javamall.cart.vo.CartItem;
import com.yoziming.javamall.product.controller.CategoryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author: yoziming
 * @Date: 2022/2/23 9:42
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    CategoryController categoryController;

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId) {
        cartService.deleteItem(skuId);
        return "redirect:http://localhost:11000/cart/cart.html";
    }

    @GetMapping("/countItem")
    public String countItem(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num) {
        cartService.changeItemCount(skuId, num);
        return "redirect:http://localhost:11000/cart/cart.html";
    }

    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId,
                            @RequestParam("check") Integer check) {

        cartService.checkItem(skuId, check);
        return "redirect:http://localhost:11000/cart/cart.html";
    }

    /**
     * 瀏覽器有一個cookie, user-key:標識用戶身份，一個月後過期
     * 如果第一次使用jd的購物車功能，都會給一個臨時的用戶身份
     * 瀏覽器以後保存，每次訪問都會帶上這個cookie
     * <p>
     * 登入：session有
     * 沒登入：按照cookie裏面帶來的user-key來做
     * 第一次：如果沒有臨時用戶，幫忙創建一個臨時用戶
     *
     * @return
     */
    @GetMapping("/cart.html")
    public String cartListPage(Model model) throws ExecutionException, InterruptedException {
        R cat = categoryController.getCat();
        List<CategoryVo> category = cat.getData("category", new TypeReference<List<CategoryVo>>() {
        });
        model.addAttribute("category", category);
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartlist";
    }

    /**
     * 添加商品到購物車
     * RedirectAttributes
     * ra.addFlashAttribute();將數據放在session裏面可以在頁面取出，但是只能取一次
     * ra.addAttribute("skuId",skuId); 將數據放在請求路徑後
     *
     * @return
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                            RedirectAttributes ra) throws ExecutionException, InterruptedException {

        cartService.addToCart(skuId, num);
        //RedirectAttributes，重定向時會將參數放到地址後
        ra.addAttribute("skuId", skuId);
        ra.addAttribute("num", num);
        return "redirect:http://localhost:11000/cart/addToCartSuccess.html";
    }

    /**
     * 跳轉到成功頁：可以理解為刷新頁面時把num過濾掉，重新查詢數據展示
     *
     * @param skuId
     * @param model
     * @return
     */
    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId,
                                       @RequestParam("num") Integer num,
                                       Model model) {
        R cat = categoryController.getCat();
        List<CategoryVo> category = cat.getData("category", new TypeReference<List<CategoryVo>>() {
        });
        model.addAttribute("category", category);
        //重向到成功頁面，再次查詢購物車數據即可
        CartItem cartItem = cartService.getCartItem(skuId);
        model.addAttribute("item", cartItem);
        model.addAttribute("num", num);

        return "cartsuccess";
    }
}
