package com.yoziming.javamall.thirdparty.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.yoziming.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: yoziming
 * @Date: 2022/01/28 17:36
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/thirdparty")
public class OssController {

    @Autowired
    OSS ossClient;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    String endpoint;
    @Value("${spring.cloud.alicloud.access-key}")
    String accessKeyId;
    @Value("${spring.cloud.alicloud.secret-key}")
    String accessKeySecret;
    @Value("${spring.cloud.alicloud.oss.bucket}")
    String bucket;

    @RequestMapping("/oss/policy")
    public R policy() {
        String host = "https://" + bucket + "." + endpoint; // host的格式為 bucketname.endpoint
        // callbackUrl為 上傳回調服務器的URL，請將下面的IP和Port配置為您自己的真實訊息。
        //String callbackUrl = "http://88.88.88.88:8888";

        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = format + "/"; // 用戶上傳文件時指定的前綴。

        Map<String, String> respMap = null;
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessKeyId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

        } catch (Exception e) {
            log.error("oss生成上傳訊息失敗！異常訊息={}", e.getMessage());
            return R.error();
        } finally {
            ossClient.shutdown();
        }
        log.info("生成的oss訊息：{}", JSONObject.toJSONString(respMap));
        return R.ok().put("data", respMap);
    }

}
