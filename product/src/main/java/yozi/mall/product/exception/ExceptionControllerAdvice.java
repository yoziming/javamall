package yozi.mall.product.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yozi.mall.common.exception.BizCodeEnum;
import yozi.mall.common.utils.R;

import java.util.HashMap;

@Slf4j // 印在日誌
@RestControllerAdvice(basePackages = "yozi.mall.product.controller")
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class) // 針對哪類異常
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("資料驗證異常", e.getMessage(), e.getClass()); // 印在日誌
        // 從異常中獲取資料
        BindingResult bindingResult = e.getBindingResult();
        // 裝在map中返回給前端看
        HashMap<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),
                fieldError.getDefaultMessage()));
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMessage()).put("data",
                errorMap);
    }

    // @ExceptionHandler(Throwable.class) // 所有異常
    // public R handleException(Throwable e) {
    //     return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMessage());
    // }

}


