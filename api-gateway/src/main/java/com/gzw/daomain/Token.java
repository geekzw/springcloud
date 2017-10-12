package com.gzw.daomain;

import java.io.Serializable;

/**
 * Created by gujian on 2017/7/7.
 */
public class Token implements Serializable{

    private String username;
    private String token;

    public Token() {
    }

    public Token(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
