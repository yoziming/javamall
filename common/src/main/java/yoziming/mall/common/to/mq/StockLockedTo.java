package yoziming.mall.common.to.mq;

import lombok.Data;

/**
 * 鎖定庫存成功，往延時隊列存入 工作單to 對象
 * wms_ware_order_task
 */
@Data
public class StockLockedTo {

    /**
     * 庫存工作單的id
     **/
    private Long id;

    /**
     * 庫存單詳情 wms_ware_order_task_detail
     **/
    private StockDetailTo detailTo;
}
