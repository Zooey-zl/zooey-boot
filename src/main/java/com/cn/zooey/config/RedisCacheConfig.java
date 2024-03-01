package com.cn.zooey.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Fengzl
 * @date 2023/10/9 11:02
 * @desc
 **/
@Slf4j
@Configuration
@EnableCaching
public class RedisCacheConfig {


    /**
     * 配置 RedisTemplate
     * @param connectionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        FastJsonRedisSerializer<Object> redisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);

        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;

    }


    /**
     * 配置 StringRedisTemplate
     * @param connectionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {

        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory);

        return stringRedisTemplate;
    }


    /**
     * 缓存配置(默认失效时间, 是否允许null值,序列化方式)
     * @return
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {

        return RedisCacheConfiguration.defaultCacheConfig()
                // 默认过期时间 60秒
                .entryTtl(Duration.ofSeconds(60))
                // 禁用第一层key
                .disableKeyPrefix()
                // 不允许存储null值
                // .disableCachingNullValues()
                // fastJson序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(Object.class)));

    }

    /**
     * 默认的缓存管理器配置, 支持自定义失效时间
     * @param connectionFactory
     * @return
     */
    @Primary
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 1. 第一种方式, 自定义的过期时间使用方式 (menu#60*60)
        return new MyRedisCacheManager(RedisCacheWriter.lockingRedisCacheWriter(connectionFactory),
                // 默认缓存配置
                this.redisCacheConfiguration());

    }


    /**
     * 带有常用失效时间的缓存管理器
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager ttlCacheManager(RedisConnectionFactory connectionFactory) {
        // 1. 第一种方式, 自定义的过期时间使用方式 (menu#60*60)
        // return new MyRedisCacheManager(RedisCacheWriter.lockingRedisCacheWriter(connectionFactory),
        //         // 默认缓存配置
        //         this.redisCacheConfiguration());

        // 第二种方式, 设置特定的过期时间方式 ()
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("60s", customRedisCaseConfiguration(this.redisCacheConfiguration(), Duration.ofSeconds(60)));
        cacheConfigurations.put("15m", customRedisCaseConfiguration(this.redisCacheConfiguration(), Duration.ofMinutes(15)));
        cacheConfigurations.put("30m", customRedisCaseConfiguration(this.redisCacheConfiguration(), Duration.ofMinutes(30)));
        cacheConfigurations.put("60m", customRedisCaseConfiguration(this.redisCacheConfiguration(), Duration.ofHours(1)));
        cacheConfigurations.put("12h", customRedisCaseConfiguration(this.redisCacheConfiguration(), Duration.ofHours(12)));
        cacheConfigurations.put("24h", customRedisCaseConfiguration(this.redisCacheConfiguration(), Duration.ofDays(1)));
        cacheConfigurations.put("30Day", customRedisCaseConfiguration(this.redisCacheConfiguration(), Duration.ofDays(30)));

        return RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter(connectionFactory))
                .transactionAware()
                .withInitialCacheConfigurations(cacheConfigurations)
                .cacheDefaults(this.redisCacheConfiguration())
                .build();
    }


    // 设置过期时间
    public RedisCacheConfiguration customRedisCaseConfiguration(RedisCacheConfiguration cacheConfiguration, Duration ttl) {

        return cacheConfiguration.entryTtl(ttl);
    }


}
