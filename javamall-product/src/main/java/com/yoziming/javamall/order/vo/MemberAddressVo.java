package com.yoziming.javamall.order.vo;

import lombok.Data;

/**
 * @Author: yoziming
 * @Date: 2022/2/25 16:18
 * @Description:
 */
@Data
public class MemberAddressVo {
    private Long id;
    /**
     * member_id
     */
    private Long memberId;
    /**
     * 收貨人姓名
     */
    private String name;
    /**
     * 電話
     */
    private String phone;
    /**
     * 郵政編碼
     */
    private String postCode;
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
     * 省市區代碼
     */
    private String areacode;
    /**
     * 是否預設
     */
    private Integer defaultStatus;
}
