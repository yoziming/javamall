package com.yoziming.javamall.ware.vo;

import lombok.Data;

/**
 * @Author: yoziming
 * @Date: 2022/1/19 15:10
 * @Description:
 */

@Data
public class LockStockResultVo {

    private Long skuId;

    private Integer num;

    /**
     * 是否鎖定成功
     **/
    private Boolean locked;

}
