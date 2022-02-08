package yoziming.mall.common.vo;

import lombok.Data;

/**
 * 發送code換取到的結果
 */
@Data
public class SocialUser {
    private String access_token;
    private String remind_in;
    private long expires_in;
    private String uid;
    private String isRealName;
}
