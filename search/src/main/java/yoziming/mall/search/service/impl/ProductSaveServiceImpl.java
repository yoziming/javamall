package yoziming.mall.search.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yoziming.mall.common.es.SkuEsModel;
import yoziming.mall.search.config.ElasticConfig;
import yoziming.mall.search.constant.EsConstant;
import yoziming.mall.search.service.ProductSaveService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("productSaveService")
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    RestHighLevelClient client;

    /**
     * 保存數據到es：
     * 1、創建單個保存請求
     * IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX)
     * 2、請求綁定數據
     * indexRequest.source(JSON.toJSONString(model), XContentType.JSON)
     * 3、循環構建批量保存請求
     * BulkRequest.add(indexRequest)
     * 4、使用客戶端發送批量保存請求
     * client.bulk(bulkRequest, mallElasticSearch.COMMON_OPTIONS)
     */
    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        // 保存到ES
        // 1、給es中建立索引、product，建立映射關係

        // 2、給es中保存這些數據【使用bulk，不使用index一條條保存】
        // BulkRequest bulkRequest, RequestOptions options
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel model : skuEsModels) {
            // 構造保存請求，設置索引+id
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            String s = JSON.toJSONString(model);
            // 綁定請求與數據
            indexRequest.source(s, XContentType.JSON);
            // 添加到批量保存
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = client.bulk(bulkRequest, ElasticConfig.COMMON_OPTIONS);
        // TODO 如果批量錯誤
        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.info("商品上架完成：{},返回數據：{}", collect, bulk);

        return !b;// 因為b代表的是是否有異常
    }
}
