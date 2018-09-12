package com.qimiaochong.common.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author: NightWish
 * @create: 2018-09-12 14:52
 * @description: 自定义session Dao
 **/
public class ShiroSessionDao extends CachingSessionDAO {
    //redis中session的名称前缀
    private static final String PREFIX="shiro_session_id:";

    private static final int EXPIRE_TIME=1800;

    private RedisTemplate redisTemplate;

    public ShiroSessionDao(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId=this.generateSessionId(session);
        redisTemplate.opsForValue().set(getKey(sessionId),session,EXPIRE_TIME,TimeUnit.SECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session= (Session) redisTemplate.opsForValue().get(getKey(sessionId));
        refreshSession(getKey(sessionId));
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
        Serializable sessionId=session.getId();
        redisTemplate.opsForValue().set(getKey(sessionId),session);
        refreshSession(getKey(sessionId));
    }

    @Override
    protected void doDelete(Session session) {
        Serializable sessionId=session.getId();
        redisTemplate.delete(getKey(sessionId));
    }

    private String getKey(Serializable sessionId){
        return PREFIX+sessionId.toString();
    }

    private void refreshSession(String key){
        redisTemplate.expire(key,EXPIRE_TIME,TimeUnit.SECONDS);
    }
}
