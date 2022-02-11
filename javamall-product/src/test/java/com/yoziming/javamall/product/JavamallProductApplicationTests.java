package com.yoziming.javamall.product;


import com.alibaba.fastjson.JSON;
import com.yoziming.common.es.SkuEsModel;
import com.yoziming.javamall.product.entity.BrandEntity;
import com.yoziming.javamall.product.service.BrandService;
import com.yoziming.javamall.product.service.SpuInfoService;
import com.yoziming.javamall.config.ElasticsearchConfig;
import com.yoziming.javamall.search.constant.EsConstant;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@SpringBootTest
class JavamallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    private RestHighLevelClient esRestClient;

    @Autowired
    private SpuInfoService spuInfoService;
    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("apple");
        brandService.save(brandEntity);

    }
    @Test
    public void ossTest() throws FileNotFoundException {
        /*FileInputStream fileInputStream = new FileInputStream("D:\\Users\\Desktop\\文档\\gulimall资料源码\\docs\\pics\\apple.png");
        ossClient.putObject("sshdg-javamall", "apple.png", fileInputStream);
        ossClient.shutdown();
        System.out.println("end");*/
    }

    @Test
    public void esTest() throws IOException {


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("spuId","15"));
        searchSourceBuilder.query(boolQueryBuilder);
        log.info("构建的DSL语句 {}",searchSourceBuilder.toString());
        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX},searchSourceBuilder);

        SearchResponse response = esRestClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
            String sourceAsString = hit.getSourceAsString();
            String id = hit.getId();
            System.out.println(id);
            SkuEsModel esModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
            System.out.println(esModel);
        }
    }
    @Test
    public void es2Test() throws IOException {


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("account_number","1"));
        searchSourceBuilder.query(boolQueryBuilder);
        log.info("构建的DSL语句 {}",searchSourceBuilder.toString());
        SearchRequest searchRequest = new SearchRequest(new String[]{"bank"},searchSourceBuilder);

        SearchResponse response = esRestClient.search(searchRequest, ElasticsearchConfig.COMMON_OPTIONS);

        SearchHits hits = response.getHits();

        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(hit);
            String id = hit.getId();
            System.out.println(id);
            DeleteRequest deleteRequest = new DeleteRequest("bank", id);
            DeleteResponse deleteResponse = esRestClient.delete(deleteRequest, ElasticsearchConfig.COMMON_OPTIONS);
            System.out.println(deleteResponse.toString());
        }



    }

    @Test
    void publishTest() {


        boolean publish = spuInfoService.isPublish(11l);
        System.out.println(publish);
    }
}
