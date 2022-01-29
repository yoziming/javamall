package yozi.mall.cart.interceptor;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import yozi.mall.common.constant.AuthServerConstant;
import yozi.mall.common.constant.CartConstant;
import yozi.mall.common.to.MemberResponseTo;
import yozi.mall.common.to.UserInfoTo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 在執行目標方法之前，判斷用戶的登入狀態.並封裝傳遞給controller目標請求
 */
public class CartInterceptor implements HandlerInterceptor {
    public static ThreadLocal<UserInfoTo> toThreadLocal = new ThreadLocal<>();

    /***
     * 攔截所有請求給ThreadLocal封裝UserInfoTo物件
     * 1、從session中獲取MemberResponseVo != null，登入狀態，為UserInfoTo設置Id
     * 2、從request中獲取cookie，找到user-key的value，
     * 目標方法執行之前：在ThreadLocal中存入用戶信息【同一個線程共享數據】
     * 從session中獲取數據【使用session需要cookie中的GULISESSION 值】
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfoTo userInfoTo = new UserInfoTo();
        HttpSession session = request.getSession();
        // 獲得當前登入用戶的信息
        MemberResponseTo memberResponseTo = (MemberResponseTo) session.getAttribute(AuthServerConstant.LOGIN_USER);
        if (memberResponseTo != null) {
            // 用戶登入了
            userInfoTo.setUserId(memberResponseTo.getId());
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                // user-key
                String name = cookie.getName();
                if (name.equals(CartConstant.TEMP_USER_COOKIE_NAME)) {
                    userInfoTo.setUserKey(cookie.getValue());
                    // 標識客戶端已經存儲了 user-key
                    userInfoTo.setTempUser(true);
                }
            }
        }
        // 如果沒有臨時用戶一定分配一個臨時用戶UUID
        if (StringUtils.isEmpty(userInfoTo.getUserKey())) {
            String uuid = UUID.randomUUID().toString();
            userInfoTo.setUserKey(uuid);
        }
        // 目標方法執行之前
        toThreadLocal.set(userInfoTo);
        return true;
    }

    /**
     * 業務執行之後，讓瀏覽器保存臨時用戶user-key的Cookie
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 獲取當前用戶的值
        UserInfoTo userInfoTo = toThreadLocal.get();
        // 1、判斷是否登入；2、判斷是否創建user-token的cookie
        if (userInfoTo != null && !userInfoTo.isTempUser()) {
            // 創建一個cookie
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userInfoTo.getUserKey());
            cookie.setDomain("mall.com");
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
            response.addCookie(cookie);
        }
    }
}
