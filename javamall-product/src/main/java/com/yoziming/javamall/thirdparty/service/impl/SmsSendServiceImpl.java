package com.yoziming.javamall.thirdparty.service.impl;

import com.yoziming.javamall.thirdparty.service.SmsSendService;
import org.springframework.stereotype.Service;

/**
 * @Author: yoziming
 * @Date: 2022/2/7 20:35
 */
@Service
public class SmsSendServiceImpl implements SmsSendService {

    @Override
    public void EmailSendCode(String phone, String code) {
        // MailAccount account = new MailAccount();
        // account.setUser("sshdg140135");
        // account.setFrom("Javamall<sshdg140135@139.com>");
        // account.setPass("bcf9de185823a6309d00");
        // MailUtil.send(account, CollUtil.newArrayList(phone), "Javamall电子商城", "您的驗證碼為" + code, false);

        // MailAccount account = new MailAccount();
        // account.setHost("smtp.yeah.net");
        // account.setAuth(true);
        // account.setFrom("hutool@yeah.net");
        // account.setUser("hutool");
        // account.setPass("q1w2e3");
        // MailUtil.send(account, CollUtil.newArrayList("hutool@foxmail.com"), "測試", "Javamall商城" + "您的驗證碼為" + code,
        //         false);
        System.out.println("驗證碼 code = " + code);
    }

    @Override
    public void AliyunSendCode(String phone) {

    }
}
