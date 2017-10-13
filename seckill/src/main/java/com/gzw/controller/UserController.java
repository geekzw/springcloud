package com.gzw.controller;

import com.alibaba.fastjson.JSON;
import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.User;
import com.gzw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gujian on 2017/10/11.
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/do_register")
    public String register(User user){
        ResultInfo resultInfo = userService.register(user);

        return resultInfo.getString(resultInfo);
    }

    @PostMapping(value = "/do_login")
    public String login(User user){
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResultInfo resultInfo = userService.login(user);

        return resultInfo.getString(resultInfo);
    }

    @GetMapping(value = "/user")
    public String getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        ResultInfo resultInfo = userService.getUser(token);

        return resultInfo.getString(resultInfo);
    }

}
