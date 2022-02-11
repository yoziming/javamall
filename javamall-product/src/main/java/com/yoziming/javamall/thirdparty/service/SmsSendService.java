package com.yoziming.javamall.thirdparty.service;

/**
 * @Author: yoziming
 * @Date: 2022/2/7 20:28
 */
public interface SmsSendService {

    void EmailSendCode(String phone, String code);

    void AliyunSendCode(String phone);
}
