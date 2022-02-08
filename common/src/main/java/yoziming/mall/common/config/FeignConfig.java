package yoziming.mall.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * feign攔截器功能
 * 解決feign 遠程請求頭丟失問題
 **/
@Configuration
public class FeignConfig {

    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                System.out.println("feign遠程調用，攔截包裝請求頭");

                // 使用RequestContextHolder拿到剛進來的請求數據
                ServletRequestAttributes requestAttributes =
                        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (requestAttributes != null) {
                    HttpServletRequest request = requestAttributes.getRequest();//老請求
                    if (request != null) {
                        // 同步請求頭的數據（主要是cookie）
                        // 把老請求的cookie值放到新請求上來，進行一個同步
                        String cookie = request.getHeader("Cookie");
                        template.header("Cookie", cookie);
                    }
                }
            }
        };
        return requestInterceptor;
    }
}
