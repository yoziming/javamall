package yozi.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品spu積分設置
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:26
 */
@Data
@TableName("sms_spu_bounds")
public class SpuBoundsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long spuId;
	/**
	 * 成長積分
	 */
	private BigDecimal growBounds;
	/**
	 * 購物積分
	 */
	private BigDecimal buyBounds;
	/**
	 * 優惠生效情況[1111（四個狀態位，從右到左）;0 - 無優惠，成長積分是否贈送;1 - 無優惠，購物積分是否贈送;2 - 有優惠，成長積分是否贈送;3 - 有優惠，購物積分是否贈送【狀態位0：不贈送，1：贈送】]
	 */
	private Integer work;

}
