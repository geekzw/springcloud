package com.gzw.controller;

import com.gzw.daomain.Order;
import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.enums.OrderStatus;
import com.gzw.dto.PayRequest;
import com.gzw.service.OrderService;
import com.gzw.service.PayServiceR;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gujian on 2017/10/11.
 */
@RestController
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping(value = "/getOrderByOrderNo")
    public String getOrderByOrderNo(@RequestParam("orderNo") String orderNo){

        ResultInfo resultInfo;
        resultInfo = orderService.findByOrderNo(orderNo);
        return ResultInfo.getString(resultInfo);
    }

    @PostMapping(value = "/getOrderByUserId")
    public String getOrderByUserId(@RequestParam("UserId") Integer userId){

        ResultInfo resultInfo;
        resultInfo = orderService.findByUserId(userId);
        return ResultInfo.getString(resultInfo);
    }

    @PostMapping(value = "/kill")
    public String kill(@RequestParam("id") Integer id, HttpServletRequest request){
        ResultInfo resultInfo;

        resultInfo = orderService.addOrder(id,request);

        return ResultInfo.getString(resultInfo);

    }

    @PostMapping(value = "/pay")
    public String pay(@RequestParam("orderNo") String orderNo){

        ResultInfo resultInfo = orderService.pay(orderNo);

        return ResultInfo.getString(resultInfo);
    }
}
