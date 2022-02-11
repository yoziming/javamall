package com.yoziming.javamall.interceptor;

import com.yoziming.common.constant.AuthServerConstant;
import com.yoziming.common.vo.MemberRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:30
 * @Description:
 */
@Component
@Slf4j
public class LoginUserInterceptor implements HandlerInterceptor {
    public static ThreadLocal<MemberRespVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("請求URL:{}", request.getRequestURL());
        MemberRespVo attribute = (MemberRespVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        log.info("member={}", attribute);
        if (attribute != null) {
            loginUser.set(attribute);
            return true;
        } else {
            //沒登入就去登入
            request.getSession().setAttribute("msg", "請先進行登入");
            response.sendRedirect("http://localhost:11000/auth/login.html");
            return false;
        }
    }
}

