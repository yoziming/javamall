package yozi.mall.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuReductionTo {

    private Long skuId;
    // 打幾折
    private BigDecimal discount;
    private Integer priceStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private Integer countStatus;
    // 滿幾件
    private Integer fullCount;

    private List<MemberPriceItem> memberPrice;
}
