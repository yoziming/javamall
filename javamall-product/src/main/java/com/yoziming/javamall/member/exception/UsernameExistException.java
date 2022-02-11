package com.yoziming.javamall.member.exception;

/**
 * @Author: yoziming
 * @Date: 2022/2/15 21:03
 */
public class UsernameExistException extends RuntimeException {
    public UsernameExistException() {
        super("用戶名已存在");
    }
}
