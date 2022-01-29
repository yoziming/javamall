package yozi.mall.common.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 註冊使用的vo
 */
@Data
public class UserRegisterVo {

    @NotEmpty(message = "用戶名必須提交")
    @Length(min = 5, max = 19, message = "用戶名長度必須是5-18字符")
    private String userName;

    @NotEmpty(message = "密碼必須填寫")
    @Length(min = 5, max = 18, message = "密碼長度必須是5—18位字符")
    private String password;

    @NotEmpty(message = "手機號必須填寫")
    private String phone;

    @NotEmpty(message = "驗證碼必須填寫")
    private String code;

}
