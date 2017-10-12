package com.gzw.daomain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by gujian on 2017/7/7.
 */
@Data
public class Token implements Serializable{

    private String username;
    private String token;
    private Integer userId;

    public Token() {
    }

    public Token(Integer userId,String username, String token) {
        this.userId = userId;
        this.username = username;
        this.token = token;
    }

}
