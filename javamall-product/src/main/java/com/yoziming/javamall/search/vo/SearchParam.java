package com.yoziming.javamall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/1/30 15:35
 */

@Data
public class SearchParam {

    /**
     * 頁面傳遞過來的全文匹配關鍵字
     */
    private String keyword;

    /**
     * 品牌id,可以多選
     */
    private List<Long> brandId;

    /**
     * 三級分類id
     */
    private Long catalog3Id;

    /**
     * 排序條件：sort=price/salecount/hotscore_desc/asc
     */
    private String sort;

    /**
     * 是否顯示有貨
     */
    private Integer hasStock;

    /**
     * 價格區間查詢
     */
    private String skuPrice;

    /**
     * 按照屬性進行篩選
     */
    private List<String> attrs;

    /**
     * 頁碼
     */
    private Integer pageNum = 1;

    /**
     * 原生的所有查詢條件
     */
    private String _queryString;

}
