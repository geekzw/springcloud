package com.gzw.controller;

import com.gzw.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gujian on 2017/9/30.
 */
@RestController
public class UserController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/user/name")
    public String getUsername(){
        return userInfoService.getUserName();
    }
}
