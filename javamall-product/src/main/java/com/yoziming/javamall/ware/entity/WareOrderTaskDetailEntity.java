package com.yoziming.javamall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 庫存工作單
 *
 * @author yoziming
 * @date 2022-01-20 17:41:43
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("t_ware_order_task_detail")
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
     * 鎖定狀態
     * 1-鎖定 2-解鎖 3-扣減
     */
    private Integer lockStatus;

}
