package yoziming.mall.authserver.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MallAuthServerSentinelConfig {

    //    public MallAuthServerSentinelConfig() {
    //
    //        WebCallbackManager.setUrlBlockHandler(new UrlBlockHandler() {
    //            @Override
    //            public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException ex)
    //            throws IOException {
    //                R error = R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMessage
    //                ());
    //                response.setCharacterEncoding("UTF-8");
    //                response.setContentType("application/json");
    //                response.getWriter().write(JSON.toJSONString(error));
    //
    //            }
    //        });
    //
    //    }

}
