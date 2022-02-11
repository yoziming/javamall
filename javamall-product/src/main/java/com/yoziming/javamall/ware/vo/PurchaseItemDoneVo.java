package com.yoziming.javamall.ware.vo;

import lombok.Data;

/**
 * @Author: yoziming
 * @Date: 2022/1/19 15:10
 * @Description:
 */

@Data
public class PurchaseItemDoneVo {

    private Long itemId;

    /**
     * 狀態[0新建，1已分配，2正在採購，3已完成，4採購失敗]
     */
    private Integer status;

    private String reason;

}
