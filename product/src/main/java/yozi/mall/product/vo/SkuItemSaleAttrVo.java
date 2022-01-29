package yozi.mall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SkuItemSaleAttrVo {
    /**
     * 每一個銷售屬性都有attrName，
     * 並且有多個attrValues
     */
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}
