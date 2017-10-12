package com.gzw.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by gujian on 2017/10/10.
 */
public class PayRequest implements Serializable {

    private String orderNo;

    private BigDecimal price;

    public PayRequest(String orderNo, BigDecimal price) {
        this.orderNo = orderNo;
        this.price = price;
    }

    public PayRequest() {
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
