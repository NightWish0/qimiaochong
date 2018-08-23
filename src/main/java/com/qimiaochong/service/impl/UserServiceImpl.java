package com.qimiaochong.service.impl;

import com.qimiaochong.common.TopicStatus;
import com.qimiaochong.dao.TopicDao;
import com.qimiaochong.dao.UserDao;
import com.qimiaochong.entity.Topic;
import com.qimiaochong.entity.User;
import com.qimiaochong.service.TopicService;
import com.qimiaochong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User queryUser(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }
}
