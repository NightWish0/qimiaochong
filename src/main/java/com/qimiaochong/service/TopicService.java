package com.qimiaochong.service;

import com.qimiaochong.entity.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> findAllTopics(Integer pageNum,Integer pageSize);

    Topic findOne(Long id);

    boolean updateOfUser(Topic topic);

    boolean create(Topic topic);

    boolean ban(Long id);

    boolean unBan(Long id);

    boolean delete(Long id);

    boolean destroy(Long id);


}
