package yoziming.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品滿減信息
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:26
 */
@Data
@TableName("sms_sku_full_reduction")
public class SkuFullReductionEntity implements Serializable {
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
     * 滿多少
     */
    private BigDecimal fullPrice;
    /**
     * 減多少
     */
    private BigDecimal reducePrice;
    /**
     * 是否參與其他優惠
     */
    private Integer addOther;

}
