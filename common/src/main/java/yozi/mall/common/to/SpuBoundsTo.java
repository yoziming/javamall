package yozi.mall.common.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpuBoundsTo {
    private Long spuId;
    private BigDecimal growBounds;
    private BigDecimal buyBounds;
}
