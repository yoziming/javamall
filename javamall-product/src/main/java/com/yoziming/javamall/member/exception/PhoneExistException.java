package com.yoziming.javamall.member.exception;

/**
 * @Author: yoziming
 * @Date: 2022/2/15 21:03
 */
public class PhoneExistException extends RuntimeException {
    public PhoneExistException() {
        super("手機號已存在");
    }
}
