package com.gzw.service;

import com.gzw.daomain.Order;
import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.enums.OrderStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gujian on 2017/10/11.
 */
public interface OrderService {

    ResultInfo addOrder(Integer comId, HttpServletRequest request);

    ResultInfo findByOrderNo(String orderNo);

    ResultInfo findByUserId(Integer userId);

    ResultInfo updateStatus(String orderNo,OrderStatus orderStatus);

}