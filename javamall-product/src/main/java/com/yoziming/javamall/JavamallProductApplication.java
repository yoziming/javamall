package com.yoziming.javamall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@MapperScan({"com.yoziming.javamall.product.dao", "com.yoziming.javamall.ware.dao", "com.yoziming.javamall.member" +
        ".dao", "com.yoziming.javamall.order.dao", "com.yoziming.javamall.cart.dao"})
@EnableDiscoveryClient
@EnableCaching
@EnableRedisHttpSession
public class JavamallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavamallProductApplication.class, args);
    }

}
