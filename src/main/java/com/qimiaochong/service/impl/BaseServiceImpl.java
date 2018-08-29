package com.qimiaochong.service.impl;

import com.github.pagehelper.PageHelper;
import com.qimiaochong.common.TopicStatus;
import com.qimiaochong.common.TopicUtil;
import com.qimiaochong.dao.TopicDao;
import com.qimiaochong.dao.UserDao;
import com.qimiaochong.entity.Topic;
import com.qimiaochong.entity.User;
import com.qimiaochong.service.BaseService;
import com.qimiaochong.service.UserService;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;

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
            map.put("userName",user.getUsername());
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
    public boolean registerHandle(String loginName,String password,Model model) {
        Integer isExists=userDao.checkUserIsExists(loginName);
//        String base64Password=Base64.decodeToString();
        if (isExists==0){
            User user=new User(loginName,loginName,password);
            userDao.create(user);
            return true;
        }else{
            model.addAttribute("msg","用户名已存在");
            return false;
        }
    }
}
