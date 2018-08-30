package com.qimiaochong.service;

import com.qimiaochong.entity.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> findAllTopics(Integer pageNum,Integer pageSize);

    Topic findOne(Integer id);

    boolean updateOfUser(Topic topic);

    boolean create(Topic topic);

    boolean ban(Integer id);

    boolean unBan(Integer id);

    boolean delete(Integer id);

    boolean destroy(Integer id);


}
