package yoziming.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * sku銷售屬性&值
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Data
@TableName("pms_sku_sale_attr_value")
public class SkuSaleAttrValueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * attr_id
     */
    private Long attrId;
    /**
     * 銷售屬性名
     */
    private String attrName;
    /**
     * 銷售屬性值
     */
    private String attrValue;
    /**
     * 順序
     */
    private Integer attrSort;

}
