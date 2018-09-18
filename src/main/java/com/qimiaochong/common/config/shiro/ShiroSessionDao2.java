package com.qimiaochong.common.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author: NightWish
 * @create: 2018-09-18 15:54
 * @description:
 **/
public class ShiroSessionDao2 extends AbstractSessionDAO {

    //redis中session的名称前缀
    private static final String PREFIX="shiro_session_id:";

    private static final int EXPIRE_TIME=1800;

    private RedisTemplate redisTemplate;

    public ShiroSessionDao2(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId=this.generateSessionId(session);
        this.assignSessionId(session,sessionId);
        redisTemplate.opsForValue().set(getKey(sessionId),session,EXPIRE_TIME,TimeUnit.SECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId==null){
            return null;
        }
        Session session=null;
        if(redisTemplate.hasKey(getKey(sessionId))){
            session=(Session) redisTemplate.opsForValue().get(getKey(sessionId));
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session==null||session.getId()==null){
            return;
        }
        Serializable sessionId=session.getId();
        if(redisTemplate.hasKey(getKey(sessionId))){
            redisTemplate.opsForValue().set(getKey(sessionId),session,EXPIRE_TIME,TimeUnit.SECONDS);
        }
    }

    @Override
    public void delete(Session session) {
        if (session==null||session.getId()==null){
            return;
        }
        Serializable sessionId=session.getId();
        redisTemplate.delete(getKey(sessionId));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return redisTemplate.keys(PREFIX+"*");
    }

    private String getKey(Serializable sessionId){
        return PREFIX+sessionId.toString();
    }
}
