package yozi.mall.common.exception;

/**
 * @Description: 錯誤狀態碼枚舉
 * 錯誤碼和錯誤信息定義類
 * 1. 錯誤碼定義規則為5位數
 * 2. 前兩位表示業務場景，最後三位表示錯誤碼。例如：100001。10:通用 001:系統未知異常
 * 3. 維護錯誤碼後需要維護錯誤描述，將他們定義為枚舉形式
 * 錯誤碼列表：
 * 10: 通用
 * 001：參數格式校驗
 * 002：短信驗證碼頻率太高
 * 11: 商品
 * 12: 訂單
 * 13: 購物車
 * 14: 物流
 * 15：用戶
 **/

public enum BizCodeEnum {

    UNKNOWN_EXCEPTION(10000, "系統未知異常"),
    VALID_EXCEPTION(10001, "參數格式校驗失敗"),
    TO_MANY_REQUEST(10002, "請求流量過大，請稍後再試"),
    SMS_CODE_EXCEPTION(10002, "驗證碼獲取頻率太高，請稍後再試"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架異常"),
    PRODUCT_NULL_EXCEPTION(11099, "查無商品相關訊息"),
    USER_EXIST_EXCEPTION(15001, "存在相同的用戶"),
    PHONE_EXIST_EXCEPTION(15002, "存在相同的手機號"),
    NO_STOCK_EXCEPTION(21000, "商品庫存不足"),
    LOGINACCT_PASSWORD_EXCEPTION(15003, "賬號或密碼錯誤"),
    ;

    private Integer code;

    private String message;

    BizCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
