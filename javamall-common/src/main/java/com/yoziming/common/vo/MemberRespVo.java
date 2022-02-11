package com.yoziming.common.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: yoziming
 * @Date: 2022/2/17 16:22
 */
@ToString
@Data
public class MemberRespVo implements Serializable {
    private Long id;
    /**
     * 會員等級id
     */
    private Long levelId;
    /**
     * 用戶名
     */
    private String username;
    /**
     * 密碼
     */
    private String password;
    /**
     * 昵稱
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
     * 用戶來源
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
     * 啟用狀態
     */
    private Integer status;
    /**
     * 註冊時間
     */
    private Date createTime;

    /**
     * 社交賬號的id
     */
    private String socialUid;

    /**
     * 訪問令牌
     */
    private String accessToken;

    /**
     * 過期時間
     */
    private Long expiresIn;
}

