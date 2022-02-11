package com.yoziming.javamall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 首頁輪播廣告
 *
 * @author yoziming
 * @date 2022-01-01 17:25:51
 */
@Data
@TableName("t_product_home_adv")
public class HomeAdvEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 圖片地址
     */
    private String pic;
    /**
     * 開始時間
     */
    private Date startTime;
    /**
     * 結束時間
     */
    private Date endTime;
    /**
     * 狀態（1啟用，0關閉）
     */
    private Integer status;

    /**
     * 運營位置（1輪播圖，2品牌製造商，3新品）
     */
    private Integer type;

    /**
     * 點擊數
     */
    private Integer clickCount;
    /**
     * 廣告詳情連接地址
     */
    private String url;
    /**
     * 備註
     */
    private String note;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 發布者
     */
    private Long publisherId;
    /**
     * 審核者
     */
    private Long authId;

}
