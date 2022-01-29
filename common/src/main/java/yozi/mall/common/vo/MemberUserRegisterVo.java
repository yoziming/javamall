package yozi.mall.common.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 會員註冊Vo
 */
@Data
public class MemberUserRegisterVo {
    @NotEmpty(message = "缺少必填項目")
    private String userName;
    @NotEmpty(message = "缺少必填項目")
    private String password;
    @NotEmpty(message = "缺少必填項目")
    private String phone;
}
