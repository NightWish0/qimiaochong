package com.qimiaochong.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.qimiaochong.common.BaseStatusCode;
import com.qimiaochong.common.dao.TopicDao;
import com.qimiaochong.common.entity.Topic;
import com.qimiaochong.web.service.TopicService;
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
        return topicDao.findAll(BaseStatusCode.NORMAL_STATUS);
    }

    @Override
    public Topic findOne(Integer id) {
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
    public boolean ban(Integer id) {
        return topicDao.ban(id,new Date(),BaseStatusCode.BAN_STATUS)==1;
    }

    @Override
    public boolean unBan(Integer id) {
        return topicDao.ban(id,new Date(),BaseStatusCode.NORMAL_STATUS)==1;
    }

    @Override
    public boolean delete(Integer id) {
        return topicDao.deleteOfTemporary(id,new Date(), BaseStatusCode.DELETE_STATUS)==1;
    }

    @Override
    public boolean destroy(Integer id) {
        return topicDao.destroy(id)==1;
    }
}
