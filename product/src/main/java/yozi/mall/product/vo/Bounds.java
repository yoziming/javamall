package yozi.mall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Bounds {
    private BigDecimal growBounds;
    private BigDecimal buyBounds;
}