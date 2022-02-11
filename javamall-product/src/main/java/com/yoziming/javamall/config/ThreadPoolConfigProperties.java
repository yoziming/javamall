package com.yoziming.javamall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: yoziming
 * @Date: 2022/2/3 14:34
 * @Description:
 */
@ConfigurationProperties(prefix = "javamall.thread")
@Component
@Data
public class ThreadPoolConfigProperties {
    private Integer coreSize;
    private Integer maxSize;
    private Integer keepAliveTime;
}
