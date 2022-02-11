package com.yoziming.common.constant;

/**
 * @Author: yoziming
 * @Date: 2022/1/11 15:49
 * @Description:
 */
public class WareConstant {

    public enum PurchaseStatusEnum {
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        RECEIVE(2, "已領取"),
        FINISH(3, "已完成"),
        HASERROR(4, "有異常"),

        ;

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        PurchaseStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

    public enum PurchaseDetailStatusEnum {
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        BUYING(2, "正在採購"),
        FINISH(3, "已完成"),
        HASERROR(4, "採購失敗"),

        ;

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        PurchaseDetailStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

}
