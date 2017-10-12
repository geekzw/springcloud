package com.gzw.service;


import com.gzw.daomain.Token;
import com.gzw.daomain.User;

/**
 * Created by gujian on 2017/7/7.
 */
public interface RedisTokenService {

    Token create(User user);

    Token getToken(String authentication);

    Token getTokenByToken(String token);

    boolean checkToken(Token token);
}
