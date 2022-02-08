package yoziming.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品階梯價格
 * 
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:26
 */
@Data
@TableName("sms_sku_ladder")
public class SkuLadderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * spu_id
	 */
	private Long skuId;
	/**
	 * 滿幾件
	 */
	private Integer fullCount;
	/**
	 * 打幾折
	 */
	private BigDecimal discount;
	/**
	 * 折後價
	 */
	private BigDecimal price;
	/**
	 * 是否疊加其他優惠[0-不可疊加，1-可疊加]
	 */
	private Integer addOther;

}
