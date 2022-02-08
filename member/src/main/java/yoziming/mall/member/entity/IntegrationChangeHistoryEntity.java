package yoziming.mall.member.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 積分變化歷史記錄
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
@Data
@TableName("ums_integration_change_history")
public class IntegrationChangeHistoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * member_id
     */
    private Long memberId;
    /**
     * create_time
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 變化的值
     */
    private Integer changeCount;
    /**
     * 備註
     */
    private String note;
    /**
     * 來源[0->購物；1->管理員修改;2->活動]
     */
    private Integer sourceTyoe;

}
