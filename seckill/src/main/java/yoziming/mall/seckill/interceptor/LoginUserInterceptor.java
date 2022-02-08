package yoziming.mall.seckill.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import yoziming.mall.common.to.MemberResponseTo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import static yoziming.mall.common.constant.AuthServerConstant.LOGIN_USER;

@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberResponseTo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("/kill", uri);
        // 只有秒殺需要攔截，其他直接放行
        if (match) {
            HttpSession session = request.getSession();
            //獲取登錄的用戶信息
            MemberResponseTo attribute = (MemberResponseTo) session.getAttribute(LOGIN_USER);
            if (attribute != null) {
                //把登錄後用戶的信息放在ThreadLocal裏面進行保存
                loginUser.set(attribute);
                return true;
            } else {
                //未登錄，返回登錄頁面
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('請先進行登錄，再進行後續操作！');location.href='http://auth.mall.com/login" +
                        ".html'</script>");
                return false;
            }
        }
        return true;
    }
}
