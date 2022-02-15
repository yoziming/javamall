package com.yoziming.javamall.config;

import com.yoziming.javamall.interceptor.LoginUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: yoziming
 * @Date: 2022/2/23 15:19
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    LoginUserInterceptor loginUserInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //要攔截的路徑
        String[] addPathPatterns = {
                "/order/order/listWithItem",
                "/confirm/**",
                "/cancelOrder/**",
                "/memberOrder.html",
                "/orderList.html/**",
                "/detail.html/**",
                "/return",
                "/toTrade",
                "/submitOrder",
                "/payOrder",
                "/payed/result.html",
                "/save/address",
                "/defalt/address/**",
                "/delete/address/**",
                "/cart/**",
                "/forcePaySuccess",
        };
        //要排除的
        String[] excludePathPatterns = {
                "/order/order/status/**",
                "/payed/notify",
                "/payed/query/**",
                "/payed/query/**",
        };
        registry.addInterceptor(loginUserInterceptor)
                .addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
    }
}
