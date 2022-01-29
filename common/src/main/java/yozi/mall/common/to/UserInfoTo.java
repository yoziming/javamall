package yozi.mall.common.to;

import lombok.Data;

/**
 * 用戶信息
 **/
@Data
public class UserInfoTo {
    private Long userId;
    private String userKey; // 關聯購物車
    private boolean tempUser = false;// 客戶端是否需要存儲cookie：user-key
}
