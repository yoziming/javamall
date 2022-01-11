package yozi.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 退款信息
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
@Data
@TableName("oms_refund_info")
public class RefundInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 退款的訂單
	 */
	private Long orderReturnId;
	/**
	 * 退款金額
	 */
	private BigDecimal refund;
	/**
	 * 退款交易流水號
	 */
	private String refundSn;
	/**
	 * 退款狀態
	 */
	private Integer refundStatus;
	/**
	 * 退款渠道[1-支付寶，2-微信，3-銀聯，4-匯款]
	 */
	private Integer refundChannel;
	/**
	 * 
	 */
	private String refundContent;

}
