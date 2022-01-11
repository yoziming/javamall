package yozi.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 成長值變化歷史記錄
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
@Data
@TableName("ums_growth_change_history")
public class GrowthChangeHistoryEntity implements Serializable {
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
	private Date createTime;
	/**
	 * 改變的值（正負計數）
	 */
	private Integer changeCount;
	/**
	 * 備註
	 */
	private String note;
	/**
	 * 積分來源[0-購物，1-管理員修改]
	 */
	private Integer sourceType;

}
