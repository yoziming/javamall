package com.yoziming.javamall.thirdparty.controller;

import com.yoziming.common.utils.R;
import com.yoziming.javamall.thirdparty.service.SmsSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yoziming
 * @Date: 2022/2/7 20:28
 */
@RestController
@RequestMapping("/thirdparty/sms")
@Slf4j
public class SmsSendController {

    @Autowired
    SmsSendService smsSendService;

    /**
     * 提供給別的服務調用
     *
     * @param phone
     * @param code
     * @return
     */
    @GetMapping("/sendcode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        log.info("向{}發送驗證碼{}", phone, code);
        smsSendService.EmailSendCode(phone, code);
        return R.ok();
    }

}
