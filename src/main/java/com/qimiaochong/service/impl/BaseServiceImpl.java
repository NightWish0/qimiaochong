package com.qimiaochong.service.impl;

import com.github.pagehelper.PageHelper;
import com.qimiaochong.common.TopicStatus;
import com.qimiaochong.common.util.Md5Util;
import com.qimiaochong.common.util.TopicUtil;
import com.qimiaochong.dao.TopicDao;
import com.qimiaochong.dao.UserDao;
import com.qimiaochong.entity.Topic;
import com.qimiaochong.entity.User;
import com.qimiaochong.service.BaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
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

    @Autowired
    UserDao userDao;
    @Autowired
    TopicDao topicDao;

    @Override
    public void initIndexContent(Model model) {
        PageHelper.startPage(1,10);
        List<Topic> topics=topicDao.findAll(TopicStatus.NORMAL_STATUS);
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
    }

    @Override
    public boolean registerHandle(String loginName,String password,String authPassword,Model model) {
        User user=userDao.checkUserIsExists(loginName);
        if (loginName==null || password==null){
            return false;
        }
        if (!password.equals(authPassword)){
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
    public boolean login(String loginName, String password,Model model) {
        Subject subject=SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            UsernamePasswordToken token=new UsernamePasswordToken(loginName,password);
            try {
                subject.login(token);
                return true;
            }catch (AuthenticationException ae){
                model.addAttribute("msg","用户名或者密码错误");
                return false;
            }
        }
        return false;
    }
}
