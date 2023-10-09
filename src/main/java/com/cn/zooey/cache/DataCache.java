package com.cn.zooey.cache;

import com.cn.zooey.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author Fengzl
 * @date 2023/10/9 13:38
 * @desc
 **/
@Slf4j
@Component
public class DataCache {

    /**
     *  指定常用失效时间的方式 但不好用
     *  @CachePut(value = "15m", key = "#global + ':' + #key", cacheManager = "ttlCacheManager")
     *  public JSONObject setJsonCache(String global, String key, JSONObject value) {
     *      System.out.println("存储数据到缓存: "+ value);
     *      return value;
     *  }
     * @param args
     * @return
     */
    public static void main(String[] args) {
    }


    @CachePut(value = "default" + GlobalConstant.TOKEN_KEY_EXPIRATION, key = "#cacheName+':'+#key")
    public String setLoginToken(String cacheName, String key, String value) {
        log.info("[登录token缓存] - 存储, cacheName -> {}, key -> {}, value -> {} ", cacheName, key, value);

        return value;
    }

    @Cacheable(value = "default" + GlobalConstant.TOKEN_KEY_EXPIRATION, key = "#cacheName+':'+#key")
    public String getLoginToken(String cacheName, String key) {
        log.info("[登录token缓存] - 获取, cacheName -> {}, key -> {} ", cacheName, key);

        return null;
    }

    @CacheEvict(value = "default" + GlobalConstant.TOKEN_KEY_EXPIRATION, key = "#cacheName+':'+#key")
    public void removeLoginToken(String cacheName, String key) {
        log.info("[登录token缓存] - 删除, cacheName -> {}, key -> {} ", cacheName, key);

    }


}
