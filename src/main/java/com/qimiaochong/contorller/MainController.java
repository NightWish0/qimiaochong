package com.qimiaochong.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(){
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
