package com.qimiaochong.web.service;

import org.springframework.ui.Model;


public interface BaseService {

    void initIndexContent(Model model);

    boolean registerHandle(String loginName,String password,String authPassword,Model model);

    String loginHandle(String loginName,String password,Model model);

    void logout();
}
