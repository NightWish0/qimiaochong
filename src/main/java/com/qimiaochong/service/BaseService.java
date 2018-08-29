package com.qimiaochong.service;

import org.springframework.ui.Model;

public interface BaseService {

    void initIndexContent(Model model);

    boolean registerHandle(String loginName,String password,Model model);
}
