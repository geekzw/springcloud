package com.gzw.daomain.enums;

/**
 * Created by gujian on 2017/10/10.
 */
public enum OrderStatus {
    INVALID(-1,"无效"),
    WAITE_PAY(0,"秒杀成功待付款"),
    DELIVERY(1,"配送中"),
    RECEIVED(2,"已签收"),
    CLOSE(3,"关闭"),
    CANCEL(4,"取消"),
    ;


    OrderStatus(Integer code, String des) {
        this.code = code;
        this.des = des;
    }

    private Integer code;

    private String des;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
