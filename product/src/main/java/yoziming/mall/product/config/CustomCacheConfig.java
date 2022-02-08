package yoziming.mall.product.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CustomCacheConfig {
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // config = config.entryTtl();
        config =
                config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new
                        StringRedisSerializer()));
        config =
                config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new
                        GenericJackson2JsonRedisSerializer()));

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        // 讓application.properties文件中的配置生效
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

    // @Bean
    // public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    //     RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    //     redisTemplate.setConnectionFactory(redisConnectionFactory);
    //     Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    //
    //     // 解決查詢緩存轉換異常的問題
    //     ObjectMapper om = new ObjectMapper();
    //     // 指定要序列化的域，field,get和set,以及修飾符範圍，ANY是都有包括private和public
    //     om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    //     // 指定序列化輸入的類型，類必須是非final修飾的，final修飾的類，比如String,Integer等會跑出異常
    //     om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    //     jackson2JsonRedisSerializer.setObjectMapper(om);
    //
    //     //序列號key value
    //     redisTemplate.setKeySerializer(new StringRedisSerializer());
    //     redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    //     redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    //     redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    //
    //     redisTemplate.afterPropertiesSet();
    //     return redisTemplate;
    // }
    //
    // @Bean
    // public CacheManager cacheManager(RedisConnectionFactory factory) {
    //     RedisSerializer<String> redisSerializer = new StringRedisSerializer();
    //     Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    //
    //     // 解決查詢緩存轉換異常的問題
    //     ObjectMapper om = new ObjectMapper();
    //     om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    //     om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    //     jackson2JsonRedisSerializer.setObjectMapper(om);
    //
    //     // 配置序列化（解決亂碼的問題）,過期時間600秒
    //     RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
    //             .entryTtl(Duration.ofSeconds(60))
    //             .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
    //             .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer
    //                     (jackson2JsonRedisSerializer))
    //             .disableCachingNullValues();
    //
    //     RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
    //             .cacheDefaults(config)
    //             .build();
    //     return cacheManager;
    // }

}


