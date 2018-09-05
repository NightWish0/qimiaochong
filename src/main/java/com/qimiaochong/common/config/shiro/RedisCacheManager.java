package com.qimiaochong.common.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RedisCacheManager implements CacheManager {

    private final ConcurrentMap<String,Cache> caches=new ConcurrentHashMap<>();

    @Autowired
    private org.springframework.cache.CacheManager cacheManager;
    //注入Spring的缓存管理器
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache cache=caches.get(name);
        if (cache==null){
            org.springframework.cache.Cache springCache=cacheManager.getCache(name);
            cache=new RedisCache(springCache);
            caches.put(name,cache);
        }
        return null;
    }
}
