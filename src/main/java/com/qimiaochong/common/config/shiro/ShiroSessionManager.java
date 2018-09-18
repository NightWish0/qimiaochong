package com.qimiaochong.common.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * @author: NightWish
 * @create: 2018-09-18 13:55
 * @description: 重写retrieveSession方法为了减少多次从redis中读取session
 * （自定义redisSessionDao中的doReadSession方法）
 **/
public class ShiroSessionManager extends DefaultWebSessionManager {

    public ShiroSessionManager() {
        super();
    }

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId=getSessionId(sessionKey);
        ServletRequest request=null;
        if (sessionKey instanceof WebSessionKey){
            request=((WebSessionKey) sessionKey).getServletRequest();
        }
        if (request != null && sessionId != null){
            Session session= (Session) request.getAttribute(sessionId.toString());
            if (session != null){
                return session;
            }
        }
        Session session=super.retrieveSession(sessionKey);
        if (request != null && sessionId != null){
            request.setAttribute(sessionId.toString(),session);
        }
        return session;
    }
}
