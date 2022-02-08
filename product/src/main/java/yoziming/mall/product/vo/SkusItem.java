package yoziming.mall.product.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkusItem {
    private String skuTitle;
    private List<ImagesItem> images;
    private List<MemberPriceItem> memberPrice;
    private BigDecimal discount;
    private String skuSubtitle;
    private List<String> descar;
    private Integer priceStatus;
    private BigDecimal fullPrice;
    private String skuName;
    private BigDecimal price;
    private BigDecimal reducePrice;
    private Integer countStatus;
    private List<AttrItem> attr;
    private Integer fullCount;
}