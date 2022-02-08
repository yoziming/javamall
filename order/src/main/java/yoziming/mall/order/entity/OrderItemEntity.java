package yoziming.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 訂單項信息
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
@Data
@TableName("oms_order_item")
public class OrderItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * order_id
     */
    private Long orderId;
    /**
     * order_sn
     */
    private String orderSn;
    /**
     * spu_id
     */
    private Long spuId;
    /**
     * spu_name
     */
    private String spuName;
    /**
     * spu_pic
     */
    private String spuPic;
    /**
     * 品牌
     */
    private String spuBrand;
    /**
     * 商品分類id
     */
    private Long categoryId;
    /**
     * 商品sku編號
     */
    private Long skuId;
    /**
     * 商品sku名字
     */
    private String skuName;
    /**
     * 商品sku圖片
     */
    private String skuPic;
    /**
     * 商品sku價格
     */
    private BigDecimal skuPrice;
    /**
     * 商品購買的數量
     */
    private Integer skuQuantity;
    /**
     * 商品銷售屬性組合（JSON）
     */
    private String skuAttrsVals;
    /**
     * 商品促銷分解金額
     */
    private BigDecimal promotionAmount;
    /**
     * 優惠券優惠分解金額
     */
    private BigDecimal couponAmount;
    /**
     * 積分優惠分解金額
     */
    private BigDecimal integrationAmount;
    /**
     * 該商品經過優惠後的分解金額
     */
    private BigDecimal realAmount;
    /**
     * 贈送積分
     */
    private Integer giftIntegration;
    /**
     * 贈送成長值
     */
    private Integer giftGrowth;

}
