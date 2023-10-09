package com.cn.zooey.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.Duration;

/**
 * @author Fengzl
 * @date 2023/10/9 10:46
 * @desc 自定义redis缓存管理器
 **/
@Slf4j
public class MyRedisCacheManager extends RedisCacheManager {

    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfiguration) {
        super(cacheWriter, cacheConfiguration);
    }

    /**
     * @author Fengzl
     * @desc 设置 redis缓存的失效时间
     *      注:@Cacheable(chacheName="menu#60*60") '#'后为失效时间
     * @date 2023/10/9 10:57
     * @param name
     * @param cacheConfig
     * @return RedisCache
     **/
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        // 从name中截取 真正的name和失效时间(秒)
        String[] cells = StringUtils.delimitedListToStringArray(name, "#");
        name = cells[0];
        long ttl;
        if (cells.length > 1) {
            String strTtl = cells[1];
            // 计算出实际失效时间
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("js");
            try {
                Object eval = scriptEngine.eval(strTtl);
                ttl = Long.parseLong(String.valueOf(eval));
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
            log.info("[redis缓存] ## name -> {}, ttl -> {}(s)", name, ttl);
            // 把失效时间配置到 cacheConfig
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl));
        }

        return super.createRedisCache(name, cacheConfig);
    }

}
