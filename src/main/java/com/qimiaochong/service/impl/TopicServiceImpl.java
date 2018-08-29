package com.qimiaochong.service.impl;

import com.github.pagehelper.PageHelper;
import com.qimiaochong.common.TopicStatus;
import com.qimiaochong.dao.TopicDao;
import com.qimiaochong.entity.Topic;
import com.qimiaochong.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicDao topicDao;

    @Override
    public List<Topic> findAllTopics(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return topicDao.findAll(TopicStatus.NORMAL_STATUS);
    }

    @Override
    public Topic findOne(Long id) {
        return topicDao.findById(id);
    }

    @Override
    public boolean updateOfUser(Topic topic) {
        return topicDao.updateOfUser(topic)==1;
    }

    @Override
    public boolean create(Topic topic) {
        return topicDao.create(topic)==1;
    }

    @Override
    public boolean ban(Long id) {
        return topicDao.ban(id,new Date(),TopicStatus.BAN_STATUS)==1;
    }

    @Override
    public boolean unBan(Long id) {
        return topicDao.ban(id,new Date(),TopicStatus.NORMAL_STATUS)==1;
    }

    @Override
    public boolean delete(Long id) {
        return topicDao.delete(id,new Date(), TopicStatus.DELETE_STATUS)==1;
    }

    @Override
    public boolean destroy(Long id) {
        return topicDao.destroy(id)==1;
    }
}
