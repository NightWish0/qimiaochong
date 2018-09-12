package com.qimiaochong.common.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 缓存管理器
 */
public class ShiroRedisCacheManager implements CacheManager {
    private final String CACHE_PREFIX="shiro:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroRedisCache<K,V>(redisTemplate,CACHE_PREFIX+name);
    }
}
