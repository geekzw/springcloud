package com.gzw.service;

import com.gzw.dto.User;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by gujian on 2017/9/30.
 */
public interface UserService {

    @GetMapping("/name")
    String getUserName();

    @GetMapping("/address")
    String getUserAddress();

    @GetMapping("/all")
    User getUserInfo();
}
