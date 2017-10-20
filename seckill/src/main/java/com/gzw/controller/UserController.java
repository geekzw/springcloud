package com.gzw.controller;

import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.User;
import com.gzw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gujian on 2017/10/11.
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/users")
    public String register(@RequestBody User user){
        ResultInfo resultInfo = userService.register(user);

        return resultInfo.getString(resultInfo);
    }

    @PostMapping(value = "/token")
    public String login(@RequestBody User user){

        ResultInfo resultInfo = userService.login(user);

        return resultInfo.getString(resultInfo);
    }

    @GetMapping(value = "/users")
    public String getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        ResultInfo resultInfo = userService.getUser(token);

        return resultInfo.getString(resultInfo);
    }

}
