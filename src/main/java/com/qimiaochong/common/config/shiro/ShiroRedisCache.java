package com.qimiaochong.common.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

public class ShiroRedisCache<K,V> implements Cache<K,V> {

    private RedisTemplate<K,V> redisTemplate;

    private String cacheKey;

    public ShiroRedisCache(RedisTemplate<K, V> redisTemplate, String cacheKey) {
        this.redisTemplate = redisTemplate;
        this.cacheKey = cacheKey;
    }

    //通过key来获取对应的缓存对象
    @Override
    public V get(K key) throws CacheException {
        return redisTemplate.opsForValue().get(getKey(key));
    }

    @Override
    public V put(K key, V value) throws CacheException {
        redisTemplate.opsForValue().set(getKey(key),value);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        V v=redisTemplate.opsForValue().get(getKey(key));
        redisTemplate.delete(getKey(key));
        return v;
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

    private K getKey(Object key){
        return (K)(cacheKey+key);
    }
}
