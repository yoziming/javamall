package yozi.mall.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 無庫存拋出的異常
 */
public class NoStockException extends RuntimeException {

    @Getter
    @Setter
    private Long skuId;

    public NoStockException(Long skuId) {
        super("商品id：" + skuId + "庫存不足！");
    }

    public NoStockException(String msg) {
        super(msg);
    }

}
