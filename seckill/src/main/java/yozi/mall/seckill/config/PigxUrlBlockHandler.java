// package yozi.mall.seckill.config;
//
//
// import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
// import com.alibaba.csp.sentinel.slots.block.BlockException;
// import com.alibaba.fastjson.JSON;
// import org.springframework.stereotype.Component;
// import yozi.mall.common.utils.R;
//
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
//
// /**
//  * @Description: 自定义阻塞返回方法
//  **/
// @Component
// public class PigxUrlBlockHandler implements BlockExceptionHandler {
//
//     @Override
//     public void handle(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws
//     IOException {
//         R error = R.error(BizCodeEnume.TO_MANY_REQUEST.getCode(), BizCodeEnume.TO_MANY_REQUEST.getMsg());
//         response.setCharacterEncoding("UTF-8");
//         response.setContentType("application/json");
//         response.getWriter().write(JSON.toJSONString(error));
//     }
// }
