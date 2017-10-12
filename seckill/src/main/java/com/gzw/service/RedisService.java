package com.gzw.service;

/**
 * Created by gujian on 2017/7/19.
 */
public interface RedisService {

    void setStringValue(String key, String value);

    <T> void setObjectValue(String key, T value);

    String getStringValue(String key);

    <T> T getObjectValue(String key, Class<T> clazz);

    void setTimeOut(String key, int second);

    Long decr(String key);
}
