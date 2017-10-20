package com.gzw.service;

import com.gzw.daomain.form.OrderRequest;
import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.enums.OrderStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gujian on 2017/10/11.
 */
public interface OrderService {

    ResultInfo addOrder(Integer comId, HttpServletRequest request);

    ResultInfo findByOrderNo(String orderNo);

    ResultInfo findByUserId(HttpServletRequest request,Integer offset,Integer limit);

    ResultInfo updateStatus(String orderNo,OrderStatus orderStatus);

    ResultInfo pay(String order);

    void addToQueue(OrderRequest orderRequest);

}
