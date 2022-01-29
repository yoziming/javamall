package yozi.mall.ware.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 庫存工作單
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:44:25
 */
@Data
@TableName("wms_ware_order_task")
public class WareOrderTaskEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * order_id
     */
    private Long orderId;
    /**
     * order_sn
     */
    private String orderSn;
    /**
     * 收貨人
     */
    private String consignee;
    /**
     * 收貨人電話
     */
    private String consigneeTel;
    /**
     * 配送地址
     */
    private String deliveryAddress;
    /**
     * 訂單備註
     */
    private String orderComment;
    /**
     * 付款方式【 1:在線付款 2:貨到付款】
     */
    private Integer paymentWay;
    /**
     * 任務狀態
     */
    private Integer taskStatus;
    /**
     * 訂單描述
     */
    private String orderBody;
    /**
     * 物流單號
     */
    private String trackingNo;
    /**
     * create_time
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 倉庫id
     */
    private Long wareId;
    /**
     * 工作單備註
     */
    private String taskComment;

}
