package yozi.mall.common.to;

import lombok.Data;

import java.util.List;

/**
 * 鎖定庫存的vo
 */
@Data
public class WareSkuLockTo {
    private String orderSn;
    /**
     * 需要鎖住的所有庫存信息
     **/
    private List<OrderItemTo> locks;
}
