package com.yoziming.javamall.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yoziming
 * @Date: 2022/1/27 10:25
 * @Description:
 */
@Configuration
public class ElasticsearchConfig {

    // 設定
    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        // builder.addHeader("Authorization", "Bearer " + TOKEN);
        // builder.setHttpAsyncResponseConsumerFactory(
        //         new HttpAsyncResponseConsumerFactory
        //                 .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    // 已經被棄用的創造Client方法
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        // Create the low-level client
        RestClientBuilder httpClientBuilder = RestClient.builder(
                new HttpHost("localhost", 9200)
        );
        // Create the HLRC
        return new RestHighLevelClient(httpClientBuilder);
    }
}
// @Configuration
// public class ElasticsearchConfig {
//     // @Bean
//     // public RestHighLevelClient esRestClient(){
//     //     RestHighLevelClient client = new RestHighLevelClient(
//     //             RestClient.builder(new HttpHost("192.168.137.14", 9200, "http")));
//     //     return  client;
//     // }
//
//     public static final RequestOptions COMMON_OPTIONS;
//
//     static {
//         RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//         // builder.addHeader("Authorization", "Bearer " + TOKEN);
//         // builder.setHttpAsyncResponseConsumerFactory(
//         //         new HttpAsyncResponseConsumerFactory
//         //                 .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
//         COMMON_OPTIONS = builder.build();
//     }
//
//     @Bean
//     public RestHighLevelClient esRestClient() {
//         RestHighLevelClient client = new RestHighLevelClient(
//                 RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
//         return client;
//     }
// }
