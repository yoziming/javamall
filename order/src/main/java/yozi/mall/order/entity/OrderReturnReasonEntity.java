package yozi.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 退貨原因
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
@Data
@TableName("oms_order_return_reason")
public class OrderReturnReasonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 退貨原因名
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 啓用狀態
	 */
	private Integer status;
	/**
	 * create_time
	 */
	private Date createTime;

}
