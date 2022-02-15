package com.yoziming.javamall.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.yoziming.javamall.order.vo.PayVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author: yoziming
 * @Date: 2022/3/2 16:36
 * @Description:
 */
@Component
@Data
@Slf4j
public class AlipayTemplate {
    //在支付寶創建的應用的id
    private String app_id = "2021000116677713";

    // 商戶私鑰，您的PKCS8格式RSA2私鑰
    private String merchant_private_key =
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCWX2Ku4NEBSyehIQURGlgzaeVj0Na0mvDM8WMI0bWkpcOb9gikHLD8n27LpXas+uy+FAr1Ks7+xmmZvV03Ba2Be+sBg7SQGZYNhNew9jsHrCHCR46EJGd9ekyoVviyJpw3KX/wTPRCH6PaVyyOZ5kf4T++tglp0rgdoyovFOi0g0sjFH5L3LbyZ3n55kpBP/Oe6r7Dva8LYTol8LBObPzJev8VpSs0a02GThVWokTbrjBW/f4NIa47VdwDhl0dESQfGGO37GVDGgG+FOxohhj1a7uqi6qjDugBpvzRg0nWvkkCXvdC+BK0dFI++6Qqs6ywVATd5UPcrNX4PvOqFO4pAgMBAAECggEBAItdHfWb8Lj1MWZZVvvR4brMw0euLr819kTK1xE+Ys2Uv+TUso9+89092g6tbH7HFBDaPImaQjvIIejVowWyomqmMVi2+uque+5NlyWwH2dt9d+8N+5qWuvlJBTEfjuGIzPQp31LpPl/n3llX6sfgsAUH+MdjT3hiLgXF6qJA17/9lAHFXqx2T8kuTSi1mQflq3yoQhtGU5BxFHnCLb1Qi7iAxKJVM3hmwBh5GYQHSNueY8auYs6oucLjvlkh6735Oi3r9H9k1wwnxTZlyVB+RNNP0ZruApWdIDztRi3Jlc8O/vyRWvI4AblNrp4VqHs6bHaumq309rcZaDOehaGvYECgYEA97bdKTEPy4b1CxJEGRiyP1K8ybiSZS44olquXakoNfYALx7gGR2Ir3F9MCcFtx42gQQT2o1GZYpiu3LEx3r4OwrbR6sjn43HUCBRUXaeIZ1d+kBREBAfpnplNnldhhEazNQvsPrBlohWAqJhBZVsWwyHYh+Gn1eZ7iQJoYT7n7kCgYEAm2cANbFr+e1LmNTpVBt5qOT3oQwr8WUKvdrH0G0yF5FLa/3qlJ6U7Wt/QaHyfvK/dCivjyyJ3fIJRpHyZBuTphu1fkwXqaBZuQ66tTqSfD8luxgfDeGIkX9VEdmRp18TCCfnUqowEiwWf5gFsvgBxdiIV8hHDfHeRhy1xj+umfECgYEAkxu966B/SNc03wZR+rV35bM0+nbqObxhVcmUk3rJroIRSeqEG2Y2rjovplEGS+FdbPEG+Vu82sB8MeXOhsi4chfYcY9o8HA6VZNbt41CC6RxExTvDyMEKNG3Gcqph9zQlaPNTsUcAlGlApt8EtzSJKAkJlmikS5s88fjEFDxPcECgYBlNBb24Kyo/tiUl1O86oe4biS2RO75Lgxox+oI2pQctQqhMaNrVjs2wyME4yrBceUtCLIMU70VRWK1CqcIes0VCEaSEYv3c+36jpUZISP0tkskJPlUWORPnNUR4tbwYQJL9XC4bQ0RSJT+U0qi2tCMwA9K26sqcbGHh3r5uM9EgQKBgDoFKrtZfUzYToLzRBOmHTlVe91mJaS29iIr4jJipKSfg7jVM5t2qIQq+F1TRm/iomsZeZFZYytCcQm6+6bbrwAPGUT3mVfjlCixG25KACUtoOkn2SFPrR+O+YNrlfAN9H8xwVRsBW6xrRH2+I1a0CbKtCAh0Z/hE8UVl7sjDrpC";
    // 支付寶公鑰,查看地址：https://openhome.alipay.com/platform/keyManage.htm 對應APPID下的支付寶公鑰。
    private String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqOoGzq02M6hpwyfSwjk" +
            "/cBpSkxZPOFrBOuaKH50eVm8MVjzJ15Tk3gGcxTP9dE/pJtCQHkA+9uUE51+s8Oq84vrBLSg" +
            "+cCwu38K690TvpIE0B8AoDaDX10AqskTlCf6Drc/oBtIc0afEFr42qZgrpFLRXfu6HQB2fwsU1XNwgVZh3flht7HBexfo+8e4iAZ" +
            "/OtxPCe+/u936ACrTNd7uuFuz8w9NOdYjcfH9RL6MnUzdev866eElCSVNbgvru/CUOszOMpBLceTOsWy4IWbKXh4KTHOKD9dIR" +
            "+zBbDzJxIr1A6WfHHglp6xW22D67fnh2rsJw9eQklfVI+IHRO3V+wIDAQAB";
    // 服務器[異步通知]頁面路徑  需http://格式的完整路徑，不能加?id=123這類自定義參數，必須外網可以正常訪問
    // 支付寶會悄悄的給我們發送一個請求，告訴我們支付成功的訊息
    private String notify_url = "http://javamall2.hk1.utools.club/payed/notify/test";

    // 頁面跳轉同步通知頁面路徑 需http://格式的完整路徑，不能加?id=123這類自定義參數，必須外網可以正常訪問
    //同步通知，支付成功，一般跳轉到成功頁
    private String return_url = "http://localhost:11000/payed/result.html";

    // 簽名方式
    private String sign_type = "RSA2";

    // 字符編碼格式
    private String charset = "utf-8";

    private String timeout = "30m";

    // 支付寶網關； https://openapi.alipaydev.com/gateway.do
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    //1、根據支付寶的配置生成一個支付客戶端
    AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
            app_id, merchant_private_key, "json",
            charset, alipay_public_key, sign_type);

    public String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id,
        // AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key,
        // AlipayTemplate.sign_type);

        //2、創建一個支付請求 //設置請求參數
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商戶訂單號，商戶網站訂單系統中唯一訂單號，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金額，必填
        String total_amount = vo.getTotal_amount();
        //訂單名稱，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //會收到支付寶的響應，響應的是一個頁面，只要瀏覽器顯示這個頁面，就會自動來到支付寶的收銀台頁面

        return result;

    }

    public AlipayTradeQueryResponse queryPayResult(String outTradeNo) {

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + outTradeNo + "\"" +
                "  }");
        AlipayTradeQueryResponse response = null;
        try {
            return alipayClient.execute(request);
        } catch (AlipayApiException e) {
            log.error("支付寶查詢訂單接口異常！outTradeNo={}", outTradeNo, e);
            return null;
        }
    }

    public AlipayTradeRefundResponse refund(String outTradeNo, BigDecimal refundAmount) {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + outTradeNo + "\"," +
                "\"refund_amount\":" + refundAmount +
                "  }");
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);

            return response;
        } catch (AlipayApiException e) {
            log.error("支付寶退款調用異常！");
            return null;
        }
    }

}
