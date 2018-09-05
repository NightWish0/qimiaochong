package com.qimiaochong.web.contorller;

import com.qimiaochong.web.service.BaseService;
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
        String loginMsg=baseService.loginHandle(loginName,password,model);
        if (loginMsg.equals("admin")){
            return "redirect:/admin";
        }
        if (loginMsg.equals("user")){
            return "redirect:/";
        }
        return "redirect:/login";
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
        return "redirect:/register";
    }


}
