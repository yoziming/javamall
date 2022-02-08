package yoziming.mall.order.vo;

import lombok.Data;
import yoziming.mall.order.entity.OrderEntity;

/**
 * 提交訂單返回結果
 */
@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;

    /**
     * 錯誤狀態碼 0成功
     **/
    private Integer code;

}
