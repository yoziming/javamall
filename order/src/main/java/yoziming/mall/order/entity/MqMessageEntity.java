package yoziming.mall.order.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
@Data
@TableName("mq_message")
public class MqMessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private String messageId;
    /**
     *
     */
    private String content;
    /**
     *
     */
    private String toExchange;
    /**
     *
     */
    private String routingKey;
    /**
     *
     */
    private String classType;
    /**
     * 0-新建1-已發送2錯誤抵達3-已抵達
     */
    private Integer messageStatus;
    /**
     *
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
