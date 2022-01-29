// package yozi.mall.authserver.controller;
//
// import com.alibaba.fastjson.JSON;
// import com.alibaba.fastjson.TypeReference;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.http.HttpResponse;
// import org.apache.http.util.EntityUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import yozi.mall.common.constant.AuthServerConstant;
// import yozi.mall.common.feign.MemberFeignService;
// import yozi.mall.common.utils.R;
// import yozi.mall.common.vo.SocialUser;
//
// import javax.servlet.http.HttpSession;
// import java.util.HashMap;
// import java.util.Map;
//
// /**
//  * @Description: 處理社交登入請求
//  **/
//
// @Slf4j
// @Controller
// public class OAuth2Controller {
//
//     @Autowired
//     private MemberFeignService memberFeignService;
//
//     @GetMapping(value = "/oauth2.0/weibo/success")
//     public String weibo(@RequestParam("code") String code, HttpSession session) throws Exception {
//
//         Map<String, String> map = new HashMap<>();
//         map.put("client_id", "2129105835");
//         map.put("client_secret", "201b8aa95794dbb6d52ff914fc8954dc");
//         map.put("grant_type", "authorization_code");
//         map.put("redirect_uri", "http://auth.mall.com/oauth2.0/weibo/success");
//         map.put("code", code);
//
//         //1、根據code換取access_token
//         HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post",
//                 new HashMap<>(), map, new HashMap<>());
//
//         //2、處理
//         if (response.getStatusLine().getStatusCode() == 200) {
//             //獲取到了access_token
//             String json = EntityUtils.toString(response.getEntity());// 獲取到json串
//             //String json = JSON.toJSONString(response.getEntity());
//             SocialUser socialUser = JSON.parseObject(json, SocialUser.class);
//
//             //知道了哪個社交用戶
//             //1）、當前用戶如果是第一次進網站，自動註冊進來（為當前社交用戶生成一個會員信息，以後這個社交賬號就對應指定的會員）
//             //登入或者註冊這個社交用戶
//             System.out.println("登入後用code換取的token值：" + socialUser.getAccess_token());
//             //調用遠程服務
//             R oauthLogin = memberFeignService.oauthLogin(socialUser);
//             if (oauthLogin.getCode() == 0) {
//                 MemberResponseTo data = oauthLogin.getData("data", new TypeReference<MemberResponseTo>() {
//                 });
//                 log.info("登入成功：用戶信息：\n{}", data.toString());
//
//                 //1、第一次使用session，命令瀏覽器保存卡號，JSESSIONID這個cookie
//                 //以後瀏覽器訪問哪個網站就會帶上這個網站的cookie
//                 //TODO 1、默認發的令牌。當前域（解決子域session共享問題）
//                 //TODO 2、使用JSON的序列化方式來序列化物件到Redis中
//                 session.setAttribute(AuthServerConstant.LOGIN_USER, data);
//
//                 //2、登入成功跳回首頁
//                 return "redirect:http://mall.com";
//             } else {
//                 return "redirect:http://auth.mall.com/login.html";
//             }
//         } else {
//             return "redirect:http://auth.mall.com/login.html";
//         }
//     }
// }
