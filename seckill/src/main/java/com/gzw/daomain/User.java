package com.gzw.daomain;

import lombok.Data;

/**
 * Created by gujian on 2017/10/11.
 */
@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }
}
