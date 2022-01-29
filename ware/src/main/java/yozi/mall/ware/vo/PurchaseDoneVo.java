package yozi.mall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PurchaseDoneVo {

    @NotNull(message = "id不允許為空")
    private Long id;

    private List<PurchaseItemDoneVo> items;

}
