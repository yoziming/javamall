package com.yoziming.javamall.cart.to;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: yoziming
 * @Date: 2022/2/19 19:33
 */
@ToString
@Data
public class UserInfoTo {
    private Long userId;
    private String userKey;
    private boolean tempUser = false;
}
