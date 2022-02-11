package com.yoziming.common.to.mq;

import lombok.Data;

/**
 * @Author: yoziming
 * @Date: 2022/2/26 15:58
 * @Description:
 */
@Data
public class StockLockedTo {
    private Long id; //庫存工作單id
    private StockDetailTo detail; //工作單詳情id
}
