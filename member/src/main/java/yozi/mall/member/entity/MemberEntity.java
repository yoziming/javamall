package yozi.mall.member.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 會員
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
@Data
@TableName("ums_member")
public class MemberEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 會員等級id
     */
    private Long levelId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密碼
     */
    private String password;
    /**
     * 暱稱
     */
    private String nickname;
    /**
     * 手機號碼
     */
    private String mobile;
    /**
     * 郵箱
     */
    private String email;
    /**
     * 頭像
     */
    private String header;
    /**
     * 性別
     */
    private Integer gender;
    /**
     * 生日
     */
    private Date birth;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 職業
     */
    private String job;
    /**
     * 個性簽名
     */
    private String sign;
    /**
     * 用户來源
     */
    private Integer sourceType;
    /**
     * 積分
     */
    private Integer integration;
    /**
     * 成長值
     */
    private Integer growth;
    /**
     * 啓用狀態
     */
    private Integer status;
    /**
     * 註冊時間
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 社交登入UID
     */
    private String socialUid;
    /**
     * 社交登入TOKEN
     */
    private String accessToken;
    /**
     * 社交登入過期時間
     */
    private String expiresIn;

}
