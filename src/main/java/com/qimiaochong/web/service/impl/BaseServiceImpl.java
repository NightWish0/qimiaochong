package com.qimiaochong.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.qimiaochong.common.BaseStatusCode;
import com.qimiaochong.common.util.Md5Util;
import com.qimiaochong.common.util.TopicUtil;
import com.qimiaochong.common.dao.TopicDao;
import com.qimiaochong.common.dao.UserDao;
import com.qimiaochong.common.entity.Topic;
import com.qimiaochong.common.entity.User;
import com.qimiaochong.web.service.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseServiceImpl implements BaseService {
    public static final Logger LOGGER=LogManager.getLogger(BaseServiceImpl.class);

    @Autowired
    UserDao userDao;
    @Autowired
    TopicDao topicDao;

    @Override
    public void initIndexContent(Model model) {
        PageHelper.startPage(1,10);
        List<Topic> topics=topicDao.findAll(BaseStatusCode.TOPIC_NORMAL_STATUS);
        List<Map<String,String>> items=new ArrayList<>();
        for (Topic topic:topics){
            Map<String,String> map=new HashMap<>();
            User user=userDao.findById(topic.getUserId());
            map.put("userId",user.getId().toString());
            map.put("userName",user.getUserName());
            map.put("userAvatar",user.getAvatar());
            map.put("userProfile",user.getProfile());
            map.put("topicId",topic.getId().toString());
            map.put("topicTitle",topic.getTitle());
            map.put("topicContent",topic.getContent());
            map.put("topicLikeCount",TopicUtil.likeCountFormat(topic.getLikeCount()));
            items.add(map);
        }
        model.addAttribute("items",items);
        Session session=SecurityUtils.getSubject().getSession(false);
        if (session!=null){
            User user= (User) session.getAttribute("user");
            model.addAttribute("user",user);
        }
    }

    @Override
    public boolean registerHandle(String loginName,String password,String authPassword,Model model) {
        User user=userDao.checkUserIsExists(loginName);
        if (loginName==null || password==null){
            return false;
        }
        if (!password.equals(authPassword)){
            model.addAttribute("loginName",loginName);
            model.addAttribute("msg","两次输入密码不一致");
            return false;
        }
        Map<String,String> map=Md5Util.encodeMd5Salt(loginName,password);
        if (user == null){
            User newUser=new User(loginName,loginName,map.get("password"),map.get("salt"));
            userDao.create(newUser);
            return true;
        }else{
            model.addAttribute("loginName",loginName);
            model.addAttribute("msg","用户名已存在");
            return false;
        }
    }

    @Override
    public String loginHandle(String loginName, String password,Model model) {
        LOGGER.info("用户登录---->用户名："+loginName);
        Subject subject=SecurityUtils.getSubject();
//        Subject subject=new Subject.Builder().buildSubject();
        if (!subject.isAuthenticated()){
            UsernamePasswordToken token=new UsernamePasswordToken(loginName,password);
//            token.setRememberMe(true);
            try {
                subject.login(token);
                if (subject.hasRole("admin")){
                    return "admin";
                }
                LOGGER.info("登录成功----->用户名："+loginName);
                return "user";
            }catch(LockedAccountException lae){
                LOGGER.info("登录失败，用户被禁用----->用户名："+loginName);
                model.addAttribute("loginName",loginName);
                model.addAttribute("msg","用户已被禁用");
                return "unknown";
            }catch(AuthenticationException ae){
                LOGGER.info("登录失败，用户密码错误----->用户名："+loginName);
                model.addAttribute("loginName",loginName);
                model.addAttribute("msg","用户名或者密码错误");
                return "unknown";
            }
        }
        return "unknown";
    }

    @Override
    public void logout() {
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
    }
}
