package com.qimiaochong.common.dao;

import com.qimiaochong.common.base.BaseMapper;
import com.qimiaochong.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao extends BaseMapper<User> {

    User findById(Integer id);

    User checkUserIsExists(String loginName);

    int create(User user);
}
