package com.qimiaochong.contorller;

import com.qimiaochong.entity.Topic;
import com.qimiaochong.entity.User;
import com.qimiaochong.service.TopicService;
import com.qimiaochong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BaseController {

    @Autowired
    UserService userService;

    @Autowired
    TopicService topicService;

    @GetMapping("/")
    public String index(){
        List<Topic> topics=topicService.findAllTopics(1,10);
        return "public/index";
    }

    @GetMapping("/login")
    public String login(){
        return "public/login";
    }

    @GetMapping("/sign_in")
    public String signIn(){
        return "public/singIn";
    }


}
