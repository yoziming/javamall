package yozi.mall.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class WareSkuLockVo {

    private String orderSn;

    /**
     * 需要鎖住的所有庫存信息
     **/
    private List<OrderItemVo> locks;

}
