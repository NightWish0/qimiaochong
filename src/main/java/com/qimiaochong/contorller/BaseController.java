package com.qimiaochong.contorller;

import com.qimiaochong.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BaseController {

    @Autowired
    BaseService baseService;

    @GetMapping("/")
    public String index(Model model){
        baseService.initIndexContent(model);
        return "public/index";
    }

    @GetMapping("/login")
    public String login(){
        return "public/login";
    }

    @GetMapping("/register")
    public String register(){
        return "public/register";
    }


}
