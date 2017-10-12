package com.gzw.daomain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by gujian on 2017/10/11.
 */
@Data
public class Order {

    private String orderNo;

    private Integer userId;

    private Integer comId;

    private BigDecimal comPrice;

    private String createTime;

    private Integer secStatus;
}
