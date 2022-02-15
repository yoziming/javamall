package yoziming.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 優惠券分類關聯
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
@Data
@TableName("sms_coupon_spu_category_relation")
public class CouponSpuCategoryRelationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 優惠券id
     */
    private Long couponId;
    /**
     * 產品分類id
     */
    private Long categoryId;
    /**
     * 產品分類名稱
     */
    private String categoryName;

}