package com.yoziming.javamall.ware.vo;

import com.yoziming.javamall.order.vo.OrderItemVo;
import lombok.Data;

import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/1/19 15:10
 * @Description:
 */

@Data
public class WareSkuLockVo {

    private String orderSn;

    /**
     * 需要鎖住的所有庫存訊息
     **/
    private List<OrderItemVo> locks;

}
