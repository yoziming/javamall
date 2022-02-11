package com.yoziming.common.exception;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:43
 * @Description:
 */
public class NoStockException extends RuntimeException {
    private Long skuId;
    private String msg;

    public NoStockException(Long skuId) {
        super("商品id:" + skuId + "沒有足夠的庫存了");
    }

    public NoStockException(String msg) {
        super(msg);
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
