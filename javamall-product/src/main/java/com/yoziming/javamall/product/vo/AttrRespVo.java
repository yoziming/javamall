package com.yoziming.javamall.product.vo;

import lombok.Data;

/**
 * @Author: yoziming
 * @Date: 2022/1/11 15:53
 * @Description:
 */
@Data
public class AttrRespVo extends AttrVo {

    /**
     *
     */
    private String catalogName;

    private String groupName;

    private Long[] catalogPath;

}