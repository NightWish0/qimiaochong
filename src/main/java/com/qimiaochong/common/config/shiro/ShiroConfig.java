package com.qimiaochong.common.config.shiro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //自定义realm
    @Bean
    public Realm realm(){
        return new QmcRealm();
    }

    //redis缓存管理器
    @Bean
    public ShiroRedisCacheManager shiroRedisCacheManager(){
        return new ShiroRedisCacheManager();
    }

    //redis会话管理
    @Bean
    public ShiroSessionManager shiroSessionManager(RedisTemplate redisTemplate){
        ShiroSessionManager shiroSessionManager=new ShiroSessionManager();
        shiroSessionManager.setSessionDAO(new ShiroSessionDao2(redisTemplate));
        shiroSessionManager.setGlobalSessionTimeout(1800);
        //删除过期的session（待改进）
        shiroSessionManager.setDeleteInvalidSessions(true);
        shiroSessionManager.setSessionIdCookieEnabled(true);
        // 是否定时检查session
        shiroSessionManager.setSessionValidationSchedulerEnabled(true);

        return shiroSessionManager;
    }

    //安全管理器
    @Bean
    @Autowired
    public DefaultWebSecurityManager securityManager(RedisTemplate redisTemplate){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        securityManager.setCacheManager(shiroRedisCacheManager());
        securityManager.setSessionManager(shiroSessionManager(redisTemplate));
        return securityManager;
    }

    //过滤器
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean filterFactoryBean=new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/");
        /**
         * 配置shiro拦截器链
         * anon  不需要认证
         * authc 需要认证
         * user  验证通过或RememberMe登录的都可以
         * 当应用开启了rememberMe时,用户下次访问时可以是一个user,但不会是authc,因为authc是需要重新认证的
         * 顺序从上到下,优先级依次降低
         */
        Map<String,String> map=new LinkedHashMap<>();
        map.put("/statics/**","anon");
        map.put("/login","anon");
        map.put("/register","anon");
        map.put("/","anon");
        map.put("/**","authc");
        filterFactoryBean.setFilterChainDefinitionMap(map);
        return filterFactoryBean;
    }

    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
