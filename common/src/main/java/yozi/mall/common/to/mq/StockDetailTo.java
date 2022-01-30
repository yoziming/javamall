package yozi.mall.common.to.mq;

import lombok.Data;

/**
 * 庫存單詳情
 * wms_ware_order_task_detail
 */
@Data
public class StockDetailTo {

    private Long id;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * sku_name
     */
    private String skuName;
    /**
     * 購買個數
     */
    private Integer skuNum;
    /**
     * 工作單id
     */
    private Long taskId;

    /**
     * 倉庫id
     */
    private Long wareId;

    /**
     * 鎖定狀態
     * 1-鎖定 2-解鎖 3-扣減
     */
    private Integer lockStatus;

}
