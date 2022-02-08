package yoziming.mall.search.service;

import yoziming.mall.search.vo.SearchParam;
import yoziming.mall.search.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam param);
}
