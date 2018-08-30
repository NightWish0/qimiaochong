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
    public String loginPage(){
        return "public/login";
    }

    @PostMapping("/login")
    public String login(String loginName,String password,Model model){
        boolean isLogin=baseService.login(loginName,password,model);
        if (isLogin){
            return "public/index";
        }
        return "public/login";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "public/register";
    }

    @PostMapping("/register")
    public String register(String loginName,String password,String authPassword,Model model){
        boolean isSuccess=baseService.registerHandle(loginName,password,authPassword,model);
        if (isSuccess){
            return "public/index";
        }
        return "public/register";
    }


}
