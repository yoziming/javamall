package com.yoziming.javamall.order.vo;

import lombok.Data;

/**
 * @Author: yoziming
 * @Date: 2022/3/27 17:00
 */
@Data
public class AddressVo {

    private Long id;
    /**
     * 收貨人姓名
     */
    private String name;
    /**
     * 電話
     */
    private String phone;
    /**
     * 省份/直轄市
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 區
     */
    private String region;
    /**
     * 詳細地址(街道)
     */
    private String detailAddress;
    /**
     * 是否預設
     */
    private Integer defaultStatus;

    private Long memberId;

}
