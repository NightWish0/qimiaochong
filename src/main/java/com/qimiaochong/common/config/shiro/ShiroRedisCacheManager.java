package com.qimiaochong.common.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * 缓存管理器
 */
public class ShiroRedisCacheManager implements CacheManager {
    private String cachePrefix="shiro:";

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroRedisCache<K,V>(cachePrefix+name);
    }
}
