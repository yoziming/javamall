package yozi.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 訂單
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
@Data
@TableName("oms_order")
public class OrderEntity implements Serializable {
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
	 * 訂單號
	 */
	private String orderSn;
	/**
	 * 使用的優惠券
	 */
	private Long couponId;
	/**
	 * create_time
	 */
	private Date createTime;
	/**
	 * 用户名
	 */
	private String memberUsername;
	/**
	 * 訂單總額
	 */
	private BigDecimal totalAmount;
	/**
	 * 應付總額
	 */
	private BigDecimal payAmount;
	/**
	 * 運費金額
	 */
	private BigDecimal freightAmount;
	/**
	 * 促銷優化金額（促銷價、滿減、階梯價）
	 */
	private BigDecimal promotionAmount;
	/**
	 * 積分抵扣金額
	 */
	private BigDecimal integrationAmount;
	/**
	 * 優惠券抵扣金額
	 */
	private BigDecimal couponAmount;
	/**
	 * 後台調整訂單使用的折扣金額
	 */
	private BigDecimal discountAmount;
	/**
	 * 支付方式【1->支付寶；2->微信；3->銀聯； 4->貨到付款；】
	 */
	private Integer payType;
	/**
	 * 訂單來源[0->PC訂單；1->app訂單]
	 */
	private Integer sourceType;
	/**
	 * 訂單狀態【0->待付款；1->待發貨；2->已發貨；3->已完成；4->已關閉；5->無效訂單】
	 */
	private Integer status;
	/**
	 * 物流公司(配送方式)
	 */
	private String deliveryCompany;
	/**
	 * 物流單號
	 */
	private String deliverySn;
	/**
	 * 自動確認時間（天）
	 */
	private Integer autoConfirmDay;
	/**
	 * 可以獲得的積分
	 */
	private Integer integration;
	/**
	 * 可以獲得的成長值
	 */
	private Integer growth;
	/**
	 * 發票類型[0->不開發票；1->電子發票；2->紙質發票]
	 */
	private Integer billType;
	/**
	 * 發票抬頭
	 */
	private String billHeader;
	/**
	 * 發票內容
	 */
	private String billContent;
	/**
	 * 收票人電話
	 */
	private String billReceiverPhone;
	/**
	 * 收票人郵箱
	 */
	private String billReceiverEmail;
	/**
	 * 收貨人姓名
	 */
	private String receiverName;
	/**
	 * 收貨人電話
	 */
	private String receiverPhone;
	/**
	 * 收貨人郵編
	 */
	private String receiverPostCode;
	/**
	 * 省份/直轄市
	 */
	private String receiverProvince;
	/**
	 * 城市
	 */
	private String receiverCity;
	/**
	 * 區
	 */
	private String receiverRegion;
	/**
	 * 詳細地址
	 */
	private String receiverDetailAddress;
	/**
	 * 訂單備註
	 */
	private String note;
	/**
	 * 確認收貨狀態[0->未確認；1->已確認]
	 */
	private Integer confirmStatus;
	/**
	 * 刪除狀態【0->未刪除；1->已刪除】
	 */
	private Integer deleteStatus;
	/**
	 * 下單時使用的積分
	 */
	private Integer useIntegration;
	/**
	 * 支付時間
	 */
	private Date paymentTime;
	/**
	 * 發貨時間
	 */
	private Date deliveryTime;
	/**
	 * 確認收貨時間
	 */
	private Date receiveTime;
	/**
	 * 評價時間
	 */
	private Date commentTime;
	/**
	 * 修改時間
	 */
	private Date modifyTime;

}
