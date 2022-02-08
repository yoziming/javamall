package yoziming.mall.authserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(value = "/api/ucenter/wx")
public class WxApiController {
    //
    //    @Autowired
    //    private MemberFeignService memberFeignService;
    //
    //    /**
    //     * 獲取掃碼人的信息，添加數據
    //     * @return
    //     */
    //    @GetMapping(value = "/callback")
    //    public String callback(String code,String state, HttpSession session) throws Exception {
    //
    //        try {
    //            //得到授權臨時票據code
    //            System.out.println(code);
    //            System.out.println(state);
    //            //從redis中將state獲取出來，和當前傳入的state作比較
    //            //如果一致則放行，如果不一致則拋出異常：非法訪問
    //
    //            //向認證服務器發送請求換取access_token
    //            //2、拿着code請求 微信固定的地址，得到兩個 access_token 和 openid
    //            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
    //                    "?appid=%s" +
    //                    "&secret=%s" +
    //                    "&code=%s" +
    //                    "&grant_type=authorization_code";
    //
    //            //拼接三個參數：id 秘鑰 和 code 值
    //            String accessTokenUrl = String.format(
    //                    baseAccessTokenUrl,
    //                    ConstantWxUtils.WX_OPEN_APP_ID,
    //                    ConstantWxUtils.WX_OPEN_APP_SECRET,
    //                    code
    //            );
    //
    //            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
    //            R r = memberFeignService.weixinLogin(accessTokenInfo);
    //            if (r.getCode() == 0) {
    //                MemberResponseVo data = r.getData("data", new TypeReference<MemberResponseVo>() {});
    //                log.info("登入成功：用戶信息：{}",data.toString());
    //
    //                //1、第一次使用session，命令瀏覽器保存卡號，JSESSIONID這個cookie
    //                //以後瀏覽器訪問哪個網站就會帶上這個網站的cookie
    //                //TODO 1、默認發的令牌。當前域（解決子域session共享問題）
    //                //TODO 2、使用JSON的序列化方式來序列化物件到Redis中
    //                session.setAttribute(LOGIN_USER,data);
    //
    //                //2、登入成功跳回首頁
    //                return "redirect:http://mall.com";
    //            } else {
    //
    //                return "redirect:http://auth.mall.com/login.html";
    //            }
    //
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        return "redirect:http://auth.mall.com/login.html";
    //    }
    //
    //    /**
    //     * 生成微信掃描二維碼
    //     * @return
    //     * @throws UnsupportedEncodingException
    //     */
    //    @GetMapping(value = "/login")
    //    public String getWxCode() throws UnsupportedEncodingException {
    //
    //        //微信開發平台授權baseUrl   %s相當於？表示佔位符
    //        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
    //                "?appid=%s" +
    //                "&redirect_uri=%s" +
    //                "&response_type=code" +
    //                "&scope=snsapi_login" +
    //                "&state=%s" +
    //                "#wechat_redirect";
    //
    //        //對redirect_url進行URLEncoder編碼
    //        String redirect_url = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
    //        redirect_url = URLEncoder.encode(redirect_url,"UTF-8");
    //
    //        // 防止csrf攻擊（跨站請求偽造攻擊）
    //        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情況下會使用一個隨機數
    //        String state = "hjl.mynatapp.cc";//為了讓大家能夠使用我搭建的外網的微信回調跳轉服務器，這裡填寫你在ngrok的前置域名
    //        System.out.println("state = " + state);
    //        // 採用redis等進行緩存state 使用sessionId為key 30分鐘後過期，可配置
    //        //鍵： "wechar-open-state-" + httpServletRequest.getSession().getId()
    //        //值： satte
    //        //過期時間： 30分鐘
    //        //生成qrcodeUrl
    //
    //        //設置%s中的值
    //        String url = String.format(
    //                baseUrl,
    //                ConstantWxUtils.WX_OPEN_APP_ID,
    //                redirect_url,
    //                "atguigu"
    //        );
    //
    //        //重定向到請求微信地址
    //        return "redirect:" + url;
    //    }

}
