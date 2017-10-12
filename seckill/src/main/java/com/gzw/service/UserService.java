package com.gzw.service;

import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.User;

/**
 * Created by gujian on 2017/10/11.
 */
public interface UserService {

    ResultInfo login(User user);

    ResultInfo getUser(String token);

    ResultInfo register(User user);
}
