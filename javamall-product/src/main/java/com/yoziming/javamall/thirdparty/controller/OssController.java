// package com.yoziming.javamall.thirdparty.controller;
//
// import com.alibaba.fastjson.JSONObject;
// import com.amazonaws.HttpMethod;
// import com.amazonaws.auth.AWSCredentials;
// import com.amazonaws.auth.AWSCredentialsProvider;
// import com.amazonaws.auth.AWSStaticCredentialsProvider;
// import com.amazonaws.auth.BasicAWSCredentials;
// import com.amazonaws.auth.profile.ProfileCredentialsProvider;
// import com.amazonaws.regions.Regions;
// import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.s3.AmazonS3ClientBuilder;
// import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
// import com.yoziming.common.utils.R;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import java.net.URL;
// import java.net.URLDecoder;
// import java.util.HashMap;
// import java.util.Map;
//
// /**
//  * @Author: yoziming
//  * @Date: 2022/01/28 17:36
//  * @Description:
//  */
// @Slf4j
// @RestController
// @RequestMapping("/thirdparty")
// public class OssController {
//
//     // AWS KEY
//     private static final String AWS_ACCESS_KEY = "AKIAUI442HVBQNAFPHK5";
//     private static final String AWS_SECRET_KEY = "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";
//     private static final String BUCKET_NAME = "javamall";
//
//     /**
//      * AWS預簽名上傳
//      *
//      * @return
//      */
//     @GetMapping("/s3/policy")
//     public R generatePreSignedUrl() {
//         Map<String, Object> map = new HashMap<>();
//         try {
//             AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
//             AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
//             AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                     .withCredentials(new ProfileCredentialsProvider())
//                     .withRegion(Regions.CN_NORTH_1)
//                     .withCredentials(awsCredentialsProvider)
//                     .build();
//             java.util.Date expiration = new java.util.Date();
//             long expTimeMillis = expiration.getTime();
//             expTimeMillis += 1000 * 60 * 30;
//             expiration.setTime(expTimeMillis);
//             // String name = fileName.substring(0, fileName.lastIndexOf("."));
//             // String fileType = fileName.substring(fileName.lastIndexOf("."));
//             String prefixFileName =
//                     "_" + String.valueOf(System.currentTimeMillis()).substring(6) + ".png";
//             // Review review = new Review();
//             // review.setName(name);
//             GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME,
//                     prefixFileName)
//                     .withMethod(HttpMethod.PUT)
//                     .withExpiration(expiration);
//             URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
//             if (url == null) {
//                 log.error("找不到s3Client");
//                 return R.error();
//             }
//             // 文件訪問地址
//             StringBuilder urlImage = new StringBuilder();
//             urlImage.append(url.getProtocol()).append("://").append(url.getHost()).
//                     append(URLDecoder.decode(url.getPath(), "UTF-8"));
//             // 預簽名put地址
//             StringBuilder preUrl = new StringBuilder();
//             preUrl.append(url.getProtocol()).append("://").append(url.getHost()).
//                     append(URLDecoder.decode(url.getFile(), "UTF-8"));
//             map.put("preUrl", preUrl);
//             map.put("urlImage", urlImage);
//         } catch (Exception e) {
//             e.printStackTrace();
//             log.error("S3生成上傳訊息失敗！異常訊息={}", e.getMessage());
//             return R.error();
//         }
//         log.info("生成的S3訊息：{}", JSONObject.toJSONString(map));
//         return R.ok().put("data", map);
//     }
//
// }
