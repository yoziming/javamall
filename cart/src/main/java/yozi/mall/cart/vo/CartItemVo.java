package yozi.mall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 購物項內容
 */
public class CartItemVo {
    private Long skuId;             // skuId
    private Boolean check = true;   // 是否選中
    private String title;           // 標題
    private String image;           // 圖片
    private List<String> skuAttrValues;// 商品銷售屬性
    private BigDecimal price;       // 單價
    private Integer count;          // 當前商品數量
    private BigDecimal totalPrice;  // 總價

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getSkuAttrValues() {
        return skuAttrValues;
    }

    public void setSkuAttrValues(List<String> skuAttrValues) {
        this.skuAttrValues = skuAttrValues;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 計算當前購物項總價
     */
    public BigDecimal getTotalPrice() {
        return this.price.multiply(new BigDecimal("" + this.count));
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
