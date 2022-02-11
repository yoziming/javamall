package com.yoziming.javamall.order.vo;

import com.yoziming.javamall.order.entity.OrderEntity;
import lombok.Data;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:18
 * @Description:
 */
@Data
public class SubmitOrderResponseVo {
    private OrderEntity order;
    private Integer code; //錯誤狀態碼:0成功

}
