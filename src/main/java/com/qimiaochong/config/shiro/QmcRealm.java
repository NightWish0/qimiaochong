package com.qimiaochong.config.shiro;

import com.qimiaochong.common.util.Md5Util;
import com.qimiaochong.dao.UserDao;
import com.qimiaochong.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class QmcRealm extends AuthorizingRealm {

    @Autowired
    UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登录验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        //获得用户名
        String loginName=token.getPrincipal().toString();
        User user=userDao.checkUserIsExists(loginName);
        if (user != null){
            StringBuffer stringBuffer=new StringBuffer();
            for (char i:token.getPassword()){
                stringBuffer.append(i);
            }
            String encodePwd=Md5Util.encodeMd5Salt(loginName,stringBuffer.toString(),user.getSalt());
            if (encodePwd.equals(user.getPassword())){
                Session session=SecurityUtils.getSubject().getSession();
                session.setAttribute("user",user);
                return new SimpleAuthenticationInfo(loginName,encodePwd,"qmc_realm");
            }else{
                throw new IncorrectCredentialsException();
            }
        }else{
            throw new UnknownAccountException();
        }
    }
}
