package yoziming.mall.member.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import yoziming.mall.common.to.MemberResponseTo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static yoziming.mall.common.constant.AuthServerConstant.LOGIN_USER;

/**
 * 登入攔截器
 * 從session中獲取了登入信息（redis中），封裝到了ThreadLocal中
 */
@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberResponseTo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("/member/member/**", uri);
        boolean match1 = antPathMatcher.match("/payed/notify", uri);
        if (match || match1) {
            return true;
        }

        // 獲取登入的用戶信息
        MemberResponseTo attribute = (MemberResponseTo) request.getSession().getAttribute(LOGIN_USER);

        if (attribute != null) {
            // 把登入後用戶的信息放在ThreadLocal裡面進行保存
            loginUser.set(attribute);

            return true;
        } else {
            // 未登入，返回登入頁面
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('請先進行登入，再進行後續操作！');location.href='http://auth.mall.com/login.html'</script>");
            // session.setAttribute("msg", "請先進行登入");
            // response.sendRedirect("http://auth.mall.com/login.html");
            return false;
        }
    }
}
