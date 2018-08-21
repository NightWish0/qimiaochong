package com.qimiaochong.dao;

import com.qimiaochong.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao {

    User findById(Long id);
}
