package com.qimiaochong.dao;

import com.qimiaochong.entity.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TopicDao{

    List<Topic> findAll();

    Topic findById(Long id);

    void update(Topic topic);

    void create(Topic topic);

    void ban(Long id);

    void unBan(Long id);

    void delete(Long id);

}
