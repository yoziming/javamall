package yoziming.mall.common.to;

import lombok.Data;

/**
 * 收貨地址
 */
@Data
public class MemberAddressTo {

    private static final long serialVersionUID = 1L;

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
     * 是否默認
     */
    private Integer defaultStatus;

}
