package com.qimiaochong.service.impl;

import com.qimiaochong.dao.TopicDao;
import com.qimiaochong.entity.Topic;
import com.qimiaochong.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicDao topicDao;

    @Override
    public List<Topic> findAllTopics() {
        return topicDao.findAll();
    }
}
