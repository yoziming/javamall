package com.yoziming.javamall.product.vo;

import lombok.Data;

/**
 * @Author: yoziming
 * @Date: 2022/1/13 15:09
 * @Description:
 */
@Data
public class AttrGroupRelationVo {
    //[{"attrId":1,"attrGroupId":2}]
    private Long attrId;

    private Long attrGroupId;
}
