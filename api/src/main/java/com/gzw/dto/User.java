package com.gzw.dto;

import java.io.Serializable;

/**
 * Created by gujian on 2017/9/30.
 */
public class User implements Serializable {

    private String username;
    private String address;

    public User() {
    }

    public User(String username, String address) {
        this.username = username;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
