package yozi.mall.ware.vo;

import lombok.Data;

@Data
public class LockStockResultVo {

    private Long skuId;

    private Integer num;

    /**
     * 是否鎖定成功
     **/
    private Boolean locked;

}
