package com.qimiaochong.contorller;

import com.qimiaochong.entity.Topic;
import com.qimiaochong.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topic")
    public List<Topic> findAll(){
        return topicService.findAllTopics(1,10);
    }

    @GetMapping("/topic/{id}")
    public Topic findOne(@PathVariable Integer id){
        return topicService.findOne(id);
    }
}
