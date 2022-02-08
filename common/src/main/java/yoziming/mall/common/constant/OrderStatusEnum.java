package yoziming.mall.common.constant;

/**
 * 訂單狀態枚舉
 */

public enum OrderStatusEnum {
    CREATE_NEW(0, "待付款"),
    PAYED(1, "已付款"),
    SENDED(2, "已發貨"),
    RECIEVED(3, "已完成"),
    CANCLED(4, "已取消"),
    SERVICING(5, "售後中"),
    SERVICED(6, "售後完成");
    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
