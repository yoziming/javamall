package yozi.mall.cart.vo;

import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 購物車Vo
 * 需要計算的屬性需要重寫get方法，保證每次獲取屬性都會進行計算
 */
public class CartVo {

    private List<CartItemVo> items; // 購物項集合
    private Integer countNum;       // 商品件數【例如購物項1 2件，購物項2 3件，一共5件】
    private Integer countType;      // 商品數量，items的size()
    private BigDecimal totalAmount; // 商品總價
    private BigDecimal reduce = new BigDecimal("0.00");// 減免價格

    public List<CartItemVo> getItems() {
        return items;
    }

    public void setItems(List<CartItemVo> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int count = 0;
        if (items != null && items.size() > 0) {
            for (CartItemVo item : items) {
                count += item.getCount();
            }
        }
        return count;
    }

    public Integer getCountType() {
        return items != null ? items.size() : 0;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0");
        // 計算購物項總價
        if (!CollectionUtils.isEmpty(items)) {
            for (CartItemVo cartItem : items) {
                if (cartItem.getCheck()) {
                    amount = amount.add(cartItem.getTotalPrice());
                }
            }
        }
        // 計算優惠的價格
        return amount.subtract(getReduce());
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
