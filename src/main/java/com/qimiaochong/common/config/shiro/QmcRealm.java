package com.qimiaochong.common.config.shiro;

import com.qimiaochong.common.BaseStatusCode;
import com.qimiaochong.common.dao.SysPermissionDao;
import com.qimiaochong.common.dao.SysRoleDao;
import com.qimiaochong.common.util.Md5Util;
import com.qimiaochong.common.dao.UserDao;
import com.qimiaochong.common.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QmcRealm extends AuthorizingRealm {

    @Autowired
    UserDao userDao;
    @Autowired
    SysRoleDao sysRoleDao;
    @Autowired
    SysPermissionDao sysPermissionDao;

    /**
     * 查询权限，授权
     * 此方法调用hasRole,hasPermission的时候才会进行回调.
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     *
     * @param principalCollection 身份集合
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject subject=SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            doClearCache(principalCollection);
            subject.logout();
        }
        //授权
        SimpleAuthorizationInfo authenticationInfo=new SimpleAuthorizationInfo();
        User user= (User) subject.getSession().getAttribute("user");
        if (user!=null){
            //添加角色
            List<String> roleCodes=sysRoleDao.getRoleCodes(user.getId());
            authenticationInfo.addRoles(roleCodes);
            //添加权限
            List<String> permissionCodes=sysPermissionDao.getPermissionCodes(user.getId());
            authenticationInfo.addStringPermissions(permissionCodes);
        }
        return authenticationInfo;
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
                if(user.getStatus()==BaseStatusCode.USER_BAN_STATUS){
                    throw new LockedAccountException();
                }
                Session session=SecurityUtils.getSubject().getSession();
                session.setAttribute("user",user);
                return new SimpleAuthenticationInfo(loginName,stringBuffer.toString(),getName());
            }else{
                throw new IncorrectCredentialsException();
            }
        }else{
            throw new UnknownAccountException();
        }
    }


}
