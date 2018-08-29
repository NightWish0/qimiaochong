package com.qimiaochong.dao;

import com.qimiaochong.entity.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface TopicDao{

    List<Topic> findAll(Integer status);

    Topic findById(Long id);

    int updateOfUser(Topic topic);

    int create(Topic topic);

    int ban(@Param("id") Long id,@Param("ban_at") Date ban_at, @Param("status") Integer status);

    int delete(@Param("id") Long id,@Param("deleted_at") Date deleted_at,@Param("status") Integer status);

    int destroy(Long id);

}
