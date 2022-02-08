package yoziming.mall.cart.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import yoziming.mall.common.utils.R;

/**
 * 統一異常處理
 **/
@ControllerAdvice
public class RuntimeExceptionHandler {

    /**
     * 全局統一異常處理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public R handler(RuntimeException exception) {
        return R.error(exception.getMessage());
    }

    @ExceptionHandler(CartExceptionHandler.class)
    public R userHandler(CartExceptionHandler exception) {
        return R.error("購物車無此商品");
    }
}
