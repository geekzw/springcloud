package com.gzw.controller;

import com.gzw.dto.User;
import com.gzw.service.UserService;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gujian on 2017/9/30.
 */
@RestController("/user")
public class UserController implements UserService {
    @Override
    public String getUserName() {
        return "张三";
    }

    @Override
    public String getUserAddress() {
        return "杭州";
    }

    @Override
    public User getUserInfo() {
        User user = new User("张三","杭州");
        return user;
    }
}
