package com.gzw.service.impl;

import com.alibaba.fastjson.JSON;
import com.gzw.service.RedisService;
import com.gzw.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by gujian on 2017/7/19.
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    JedisPool jedisPool;

    @Override
    public void setStringValue(String key, String value) {
        if(StringUtil.isEmpty(key)) return ;
        Jedis jedis = jedisPool.getResource();
        jedis.set(key,value);
        jedis.close();

    }

    @Override
    public <T> void setObjectValue(String key, T value) {
        if(StringUtil.isEmpty(key)) return ;
        Jedis jedis = jedisPool.getResource();
        String obj = JSON.toJSONString(value);
        jedis.set(key,obj);
        jedis.close();
    }

    @Override
    public String getStringValue(String key) {
        if(StringUtil.isEmpty(key)) return null;
        Jedis jedis = jedisPool.getResource();
        String string = jedis.get(key);
        jedis.close();
        return string;
    }

    @Override
    public <T> T getObjectValue(String key,Class<T> clazz) {
        if(StringUtil.isEmpty(key)) return null;
        Jedis jedis = jedisPool.getResource();
        String string = jedis.get(key);
        T obj = JSON.parseObject(string,clazz);
        jedis.close();
        return obj;
    }

    @Override
    public void setTimeOut(String key,int second) {
        if(StringUtil.isEmpty(key)) return ;
        Jedis jedis = jedisPool.getResource();
        jedis.expire(key,second);
        jedis.close();
    }
}
