package com.gzw.service.impl;

import com.gzw.daomain.Token;
import com.gzw.daomain.User;
import com.gzw.service.RedisService;
import com.gzw.service.RedisTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by gujian on 2017/7/7.
 */
@Component
public class RedisTokenServiceImpl implements RedisTokenService {

    @Autowired
    RedisService redisService;

    public Token create(User user){

        String strToken = UUID.randomUUID().toString().replace("-","");
        Token token =  new Token(user.getId(),user.getUsername(),strToken);
        redisService.setObjectValue(token.getToken(),token);
        return token;

    }

    @Override
    public Token getToken(String authentication) {
        return redisService.getObjectValue(authentication,Token.class);
    }

    @Override
    public Token getTokenByToken(String token) {
        return redisService.getObjectValue(token,Token.class);
    }

    @Override
    public boolean checkToken(Token token) {
        if(token == null) return false;
        Token tokenTemp = redisService.getObjectValue(token.getUsername(),Token.class);

        if (tokenTemp == null || !tokenTemp.getToken().equals(token.getToken())) {
            return false;
        }

        redisService.setTimeOut(token.getUsername(),60*60);

        return true;

    }

}
