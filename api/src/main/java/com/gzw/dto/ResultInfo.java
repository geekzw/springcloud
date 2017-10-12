package com.gzw.dto;

import java.io.Serializable;

/**
 * Created by gujian on 2017/10/10.
 */
public class ResultInfo implements Serializable {

    private int code;

    private String des;



    public ResultInfo(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public ResultInfo() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
