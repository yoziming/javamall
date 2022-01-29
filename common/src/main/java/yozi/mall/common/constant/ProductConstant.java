package yozi.mall.common.constant;

import lombok.Getter;

@Getter
public enum ProductConstant {
    ATTR_TYPE_BASE(1, "基本屬性"),
    ATTR_TYPE_SALE(0, "銷售屬性");

    private int code;
    private String msg;

    ProductConstant(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public enum ProductStatusEnum {
        NEW_SPU(0, "新建"),
        SPU_UP(1, "商品上架"),
        SPU_DOWN(2, "商品下架"),
        ;

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        ProductStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }
}
