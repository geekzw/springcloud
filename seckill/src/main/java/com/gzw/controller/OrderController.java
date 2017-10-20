package com.gzw.controller;

import com.gzw.daomain.Constants;
import com.gzw.daomain.form.OrderRequest;
import com.gzw.daomain.ResultInfo;
import com.gzw.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gujian on 2017/10/11.
 */
@RestController
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;


    @GetMapping(value = "/orders/{orderNo}")
    public String getOrderByOrderNo(@PathVariable("orderNo") String orderNo){

        ResultInfo resultInfo;
        resultInfo = orderService.findByOrderNo(orderNo);
        return ResultInfo.getString(resultInfo);
    }

    @GetMapping(value = "/orders")
    public String getOrderByUserId(HttpServletRequest request,
                                   @RequestParam(value = "offset",defaultValue = Constants.PAGE_START) Integer offset,
                                   @RequestParam(value = "limit",defaultValue = Constants.PAGE_LIMIT) Integer limit){

        ResultInfo resultInfo;
        resultInfo = orderService.findByUserId(request,offset,limit);
        return ResultInfo.getString(resultInfo);
    }

    @PostMapping(value = "/orders")
    public DeferredResult<String> kill(@RequestParam("comId") Integer id, HttpServletRequest request){
        log.info("主线程接收请求");
        DeferredResult<String> result = new DeferredResult<String>(3000L);
        OrderRequest orderRequest = new OrderRequest(id,request,result);
        orderService.addToQueue(orderRequest);
        log.info("主线程返回");
        return result;

    }

    @PostMapping(value = "/pay/{orderNo}")
    public String pay(@PathVariable String orderNo){

        ResultInfo resultInfo = orderService.pay(orderNo);

        return ResultInfo.getString(resultInfo);
    }
}
