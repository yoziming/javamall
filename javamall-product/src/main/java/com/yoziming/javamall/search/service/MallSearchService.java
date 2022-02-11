package com.yoziming.javamall.search.service;

import com.yoziming.javamall.search.vo.SearchParam;
import com.yoziming.javamall.search.vo.SearchResult;

/**
 * @Author: yoziming
 * @Date: 2022/1/30 15:35
 */
public interface MallSearchService {

    /**
     * @param param 檢索的所有參數
     * @return 返回檢索的結果，裡面包含頁面需要的所有訊息
     */
    SearchResult search(SearchParam param);
}
