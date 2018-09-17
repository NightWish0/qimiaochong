package com.qimiaochong.common.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author: NightWish
 * @create: 2018-09-12 14:52
 * @description: 自定义session Dao
 **/
public class ShiroSessionDao extends EnterpriseCacheSessionDAO{
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
        this.assignSessionId(session,sessionId);
        redisTemplate.opsForValue().set(getKey(sessionId),session,EXPIRE_TIME,TimeUnit.SECONDS);

//        Serializable sessionId=super.doCreate(session);
//        redisTemplate.opsForValue().set(getKey(sessionId),session,EXPIRE_TIME,TimeUnit.SECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId==null){
            return null;
        }
        Session session=(Session) redisTemplate.opsForValue().get(getKey(sessionId));

//        Session session=super.doReadSession(sessionId);
//        if (session==null){
//            session= (Session) redisTemplate.opsForValue().get(getKey(sessionId));
//        }
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
        if (session==null||session.getId()==null){
            return;
        }
        Serializable sessionId=session.getId();
        redisTemplate.opsForValue().set(getKey(sessionId),session,EXPIRE_TIME,TimeUnit.SECONDS);

//        super.doUpdate(session);
//        Serializable sessionId=session.getId();
//        if (sessionId!=null){
//            redisTemplate.opsForValue().set(getKey(sessionId),session,EXPIRE_TIME,TimeUnit.SECONDS);
//        }
    }

    @Override
    protected void doDelete(Session session) {
        if (session==null||session.getId()==null){
            return;
        }
        Serializable sessionId=session.getId();
        redisTemplate.delete(getKey(sessionId));

//        super.doDelete(session);
//        Serializable sessionId=session.getId();
//        redisTemplate.delete(getKey(sessionId));
    }

    // 获取活跃的session，可以用来统计在线人数，如果要实现这个功能，
    // 可以在将session加入redis时指定一个session前缀，
    // 统计的时候则使用keys("prefix*")的方式来模糊查找redis中所有的session集合
    @Override
    public Collection<Session> getActiveSessions() {
        return redisTemplate.keys(PREFIX+"*");
    }

    private String getKey(Serializable sessionId){
        return PREFIX+sessionId.toString();
    }

}
