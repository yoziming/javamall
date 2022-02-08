package yoziming.mall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

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
        RestHighLevelClient hlrc = new RestHighLevelClient(httpClientBuilder);
        return hlrc;
    }
}
