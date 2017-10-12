package com.gzw.daomain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by gujian on 2017/10/1.
 */
@Data
public class UserIpInfo implements Serializable{

    private String token;

    private String requestIp;

    private Long requestTime;

    public UserIpInfo() {
    }

    public UserIpInfo(String token, String requestIp, Long requestTime) {
        this.token = token;
        this.requestIp = requestIp;
        this.requestTime = requestTime;
    }
}
