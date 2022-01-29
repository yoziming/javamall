package yozi.mall.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yozi.mall.cart.service.CartService;
import yozi.mall.cart.vo.CartItemVo;
import yozi.mall.cart.vo.CartVo;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class CartController {
    @Resource
    private CartService cartService;

    /**
     * 去購物車頁面的請求【未登入狀態也可以查看】
     * 瀏覽器有一個cookie:user-key 標識用戶的身份，一個月過期
     * 如果第一次使用jd的購物車功能，都會給一個臨時的用戶身份:
     * 瀏覽器以後保存，每次訪問都會帶上這個cookie；
     * 登入：session有用戶的身份
     * 沒登入：按照cookie裡面帶來user-key來做
     * 第一次，如果沒有臨時用戶，自動創建一個臨時用戶
     */
    @GetMapping(value = "/cart.html")
    public String cartListPage(Model model) throws ExecutionException, InterruptedException {
        CartVo cartVo = cartService.getCart();
        model.addAttribute("cart", cartVo);
        return "cartList";
    }

    /**
     * 添加商品到購物車
     * attributes.addFlashAttribute():將數據放在session中，可以在頁面中取出，但是只能取一次
     * attributes.addAttribute():將數據放在url後面
     *
     * @return
     */
    @GetMapping(value = "/addCartItem")
    public String addCartItem(@RequestParam("skuId") Long skuId,
                              @RequestParam("num") Integer num,
                              RedirectAttributes attributes) throws ExecutionException, InterruptedException {
        cartService.addToCart(skuId, num);
        attributes.addAttribute("skuId", skuId);// 給重定向請求用的【參數會拼接在下面請求之後】【轉發會在請求域中】
        return "redirect:http://cart.mall.com/addToCartSuccessPage.html";
    }

    /**
     * 跳轉到添加購物車成功頁面【防止重複提交】
     */
    @GetMapping(value = "/addToCartSuccessPage.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId,
                                       Model model) {
        //重定向到成功頁面。再次查詢購物車數據即可
        CartItemVo cartItemVo = cartService.getCartItem(skuId);
        model.addAttribute("cartItem", cartItemVo);
        return "success";
    }

    /**
     * 訂單服務調用：【購物車頁面點擊確認訂單時】
     * 返回所有選中的商品項【從redis中取】
     * 並且要獲取最新的商品價格信息，而不是redis中的數據
     * 獲取當前用戶的購物車所有商品項
     */
    @GetMapping(value = "/currentUserCartItems")
    @ResponseBody
    public List<CartItemVo> getCurrentCartItems() {
        List<CartItemVo> cartItemVoList = cartService.getUserCartItems();
        return cartItemVoList;
    }

    /**
     * 更改選中狀態
     */
    @GetMapping(value = "/checkItem")
    public String checkItem(@RequestParam(value = "skuId") Long skuId,
                            @RequestParam(value = "checked") Integer checked) {
        cartService.checkItem(skuId, checked);
        return "redirect:http://cart.mall.com/cart.html";
    }

    /**
     * 改變商品數量
     */
    @GetMapping(value = "/countItem")
    public String countItem(@RequestParam(value = "skuId") Long skuId,
                            @RequestParam(value = "num") Integer num) {
        cartService.changeItemCount(skuId, num);
        return "redirect:http://cart.mall.com/cart.html";
    }

    /**
     * 刪除商品信息
     */
    @GetMapping(value = "/deleteItem")
    public String deleteItem(@RequestParam("skuId") Integer skuId) {
        cartService.deleteIdCartInfo(skuId);
        return "redirect:http://cart.mall.com/cart.html";
    }
}
