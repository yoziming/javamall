package com.yoziming.javamall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/1/19 15:10
 * @Description:
 */
@Data
public class MergeVo {

    private Long purchaseId;

    private List<Long> items;

}
