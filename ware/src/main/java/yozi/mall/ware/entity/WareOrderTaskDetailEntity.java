package yozi.mall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 庫存工作單
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:44:25
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@TableName("wms_ware_order_task_detail")
public class WareOrderTaskDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
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
     * 1-已鎖定 2-已解鎖 3-已扣減
     */
    private Integer lockStatus;

}
