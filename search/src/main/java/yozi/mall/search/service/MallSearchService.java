package yozi.mall.search.service;

import yozi.mall.search.vo.SearchParam;
import yozi.mall.search.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam param);
}
