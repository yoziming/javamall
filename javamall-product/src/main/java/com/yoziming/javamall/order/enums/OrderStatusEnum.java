package com.yoziming.javamall.order.enums;

/**
 * @Author: yoziming
 * @Date: 2022/2/26 11:57
 * @Description:
 */
public enum OrderStatusEnum {

    CREATE_NEW(0, "待付款"),
    PAYED(1, "已付款"),
    SENDED(2, "已發貨"),
    RECEIVED(3, "已完成"),
    CANCEL(4, "已取消"),
    SERVICING(5, "售後中"),
    SERVICED(6, "售後完成");

    private int code;
    private String message;

    OrderStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}

