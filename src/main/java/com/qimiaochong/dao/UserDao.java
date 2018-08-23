package com.qimiaochong.dao;

import com.qimiaochong.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao extends tk.mybatis.mapper.common.Mapper<User> {

    User findById(Integer id);
}
