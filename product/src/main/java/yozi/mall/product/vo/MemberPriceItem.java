package yozi.mall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemberPriceItem {
    private BigDecimal price;
    private String name;
    private Long id;
}