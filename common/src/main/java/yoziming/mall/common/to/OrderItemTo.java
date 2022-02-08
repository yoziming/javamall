package yoziming.mall.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 購物項內容
 */
@Data
public class OrderItemTo {
    private Long skuId;             // skuId
    private Boolean check = true;   // 是否選中
    private String title;           // 標題
    private String image;           // 圖片
    private List<String> skuAttrValues;// 商品銷售屬性
    private BigDecimal price;       // 單價
    private Integer count;          // 當前商品數量
    private BigDecimal totalPrice;  // 總價
    private BigDecimal weight = new BigDecimal("0.085");// 商品重量
}
