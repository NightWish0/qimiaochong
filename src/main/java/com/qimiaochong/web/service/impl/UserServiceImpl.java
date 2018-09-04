package com.qimiaochong.web.service.impl;

import com.qimiaochong.common.dao.UserDao;
import com.qimiaochong.common.entity.User;
import com.qimiaochong.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User queryUser(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }
}
