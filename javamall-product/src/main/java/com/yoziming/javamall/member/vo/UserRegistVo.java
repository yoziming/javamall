package com.yoziming.javamall.member.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @Author: yoziming
 * @Date: 2022/2/14 22:35
 */
@Data
public class UserRegistVo {

    @NotEmpty(message = "用戶名必須填寫")
    @Length(min = 6, max = 18, message = "用戶名必須是6-18位字符")
    private String username;

    @NotEmpty(message = "密碼必須填寫")
    @Length(min = 6, max = 18, message = "用戶名必須是6-18位字符")
    private String password;

    @NotEmpty(message = "手機號必須填寫")
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "手機號格式不正確")
    private String phone;

    @NotEmpty(message = "驗證碼必須填寫")
    private String code;
}
