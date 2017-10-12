package com.gzw.controller;

import com.gzw.daomain.Order;
import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.enums.OrderStatus;
import com.gzw.dto.PayRequest;
import com.gzw.service.OrderService;
import com.gzw.service.PayServiceR;
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

    @Autowired
    PayServiceR payServiceR;

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

        ResultInfo resultInfo = orderService.findByOrderNo(orderNo);

        if(!resultInfo.isSuccess()){
            return ResultInfo.getString(resultInfo);
        }

        Order order = (Order) resultInfo.getData();

        PayRequest request = new PayRequest(order.getOrderNo(),order.getComPrice());
        com.gzw.dto.ResultInfo resultInfo1 = payServiceR.pay(request);
        if(resultInfo1.getCode() == 1){
            resultInfo = ResultInfo.getSuccessInfo(resultInfo1.getDes());
            orderService.updateStatus(orderNo, OrderStatus.DELIVERY);
        }else{
            log.error(resultInfo1.getDes());
            resultInfo = ResultInfo.getErrorMessage(resultInfo1.getDes());
        }
        return ResultInfo.getString(resultInfo);
    }
}
