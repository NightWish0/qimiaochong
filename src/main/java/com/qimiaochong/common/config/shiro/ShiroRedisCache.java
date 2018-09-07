package com.qimiaochong.common.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

public class ShiroRedisCache<K,V> implements Cache<K,V> {

    @Autowired
    private RedisTemplate<K,V> redisTemplate;

    private String cacheKey;

    public ShiroRedisCache(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    //通过key来获取对应的缓存对象
    @Override
    public V get(K key) throws CacheException {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        redisTemplate.opsForValue().set(key,value);
        return null;
    }

    @Override
    public Object remove(Object o) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set keys() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }
}
