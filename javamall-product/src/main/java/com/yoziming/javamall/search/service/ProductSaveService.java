package com.yoziming.javamall.search.service;

import com.yoziming.common.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/1/27 14:55
 * @Description:
 */
public interface ProductSaveService {

    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
