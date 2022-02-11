package com.yoziming.javamall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/1/19 15:10
 * @Description:
 */

@Data
public class PurchaseDoneVo {

    @NotNull(message = "id不允許為空")
    private Long id;

    private List<PurchaseItemDoneVo> items;

}
