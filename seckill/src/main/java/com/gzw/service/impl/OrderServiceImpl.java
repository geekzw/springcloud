package com.gzw.service.impl;

import com.gzw.daomain.Order;
import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.SecKill;
import com.gzw.daomain.Token;
import com.gzw.daomain.enums.OrderStatus;
import com.gzw.dto.PayRequest;
import com.gzw.mapper.OrderMapper;
import com.gzw.mapper.SecKillMapper;
import com.gzw.service.OrderService;
import com.gzw.service.PayServiceR;
import com.gzw.service.RedisService;
import com.gzw.service.RedisTokenService;
import com.gzw.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Random;


/**
 * Created by gujian on 2017/10/11.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    SecKillMapper secKillMapper;


    @Autowired
    RedisService redisService;

    @Autowired
    PayServiceR payServiceR;


    @Override
    @Transactional
    public ResultInfo addOrder(Integer comId, HttpServletRequest request) {

        ResultInfo resultInfo;
        String date = DateUtil.getFormatDate(new Date(System.currentTimeMillis()));
        Order order = new Order();
        String keyComObj = "seckill:"+comId;
        String keyComCount = "count:"+comId;

        order.setOrderNo(getOrderNo());
        order.setComId(comId);

        SecKill secKill = redisService.getObjectValue(keyComObj,SecKill.class);
        if(secKill == null){
            secKill = secKillMapper.getSecKillById(comId);
            redisService.setObjectValue(keyComObj,secKill);
            redisService.setStringValue(keyComCount,secKill.getStorageCount()+"");
        }

        if(secKill.getStorageCount().intValue()<=0){
            log.error("库存不足");
            resultInfo = ResultInfo.getErrorMessage("库存不足");
            return resultInfo;
        }

        if(secKill.getEndTime().compareTo(date) < 0){
            log.error("秒杀已结束");
            resultInfo = ResultInfo.getErrorMessage("秒杀已结束");
            return resultInfo;
        }

        redisService.decr(keyComCount);
        order.setComPrice(secKill.getComPrice());


        Token token = redisService.getObjectValue(request.getHeader("token"),Token.class);
        order.setUserId(token.getUserId());
        order.setSecStatus(OrderStatus.WAITE_PAY.getCode());
        int index;
        try {
            index = orderMapper.addOrder(order);
        }catch (Exception e){
            log.error("重复秒杀");
            resultInfo = ResultInfo.getErrorMessage("重复秒杀");
            return resultInfo;
        }

        if(index > 0){
            resultInfo = ResultInfo.getSuccessInfo("订单添加成功");
        }else{
            log.error("订单添加失败");
            resultInfo = ResultInfo.getErrorMessage("订单添加失败");
        }
        if(resultInfo.isSuccess()){

            int dex = secKillMapper.inCrementStorage(comId, date);
            if(dex > 0){
                resultInfo = ResultInfo.getSuccessInfo("抢购成功");
            }else{
                log.error("库存更新失败");
                resultInfo = ResultInfo.getErrorMessage("库存更新失败");
            }
        }

        return resultInfo;

    }

    @Override
    public ResultInfo findByOrderNo(String orderNo) {
        ResultInfo resultInfo;

        Order order = orderMapper.findByOrderNo(orderNo);

        if(order == null){
            resultInfo = ResultInfo.getErrorMessage("查询订单失败");
        }else{
            resultInfo = ResultInfo.getSuccessData(order);
        }


        return resultInfo;
    }

    @Override
    public ResultInfo findByUserId(Integer userId) {
        ResultInfo resultInfo;
        resultInfo = ResultInfo.getSuccessData(orderMapper.findByUserId(userId));
        return resultInfo;
    }

    @Override
    public ResultInfo updateStatus(String orderNo,OrderStatus orderStatus) {
        ResultInfo resultInfo;

        int index = orderMapper.updateStatus(orderNo,orderStatus.getCode());

        if(index > 0){
            resultInfo = ResultInfo.getSuccessInfo("订单状态更新成功");
        }else{
            resultInfo = ResultInfo.getErrorMessage("订单状态更新失败");
        }

        return resultInfo;
    }

    @Override
    public ResultInfo pay(String orderNo) {
        ResultInfo resultInfo = findByOrderNo(orderNo);

        if(!resultInfo.isSuccess()){
            return resultInfo;
        }

        Order order = (Order) resultInfo.getData();

        if(order.getSecStatus().intValue() > OrderStatus.WAITE_PAY.getCode()){
            resultInfo = ResultInfo.getErrorMessage("订单已支付，请不要重复支付");
            return resultInfo;
        }else if(order.getSecStatus().intValue() == OrderStatus.INVALID.getCode()){
            resultInfo = ResultInfo.getErrorMessage("订单异常，无法支付");
            return resultInfo;
        }

        PayRequest request = new PayRequest(order.getOrderNo(),order.getComPrice());
        com.gzw.dto.ResultInfo resultInfo1 = payServiceR.pay(request);
        if(resultInfo1.getCode() == 1){
            resultInfo = ResultInfo.getSuccessInfo(resultInfo1.getDes());
            updateStatus(orderNo, OrderStatus.DELIVERY);
        }else{
            log.error(resultInfo1.getDes());
            resultInfo = ResultInfo.getErrorMessage(resultInfo1.getDes());
        }
        return resultInfo;
    }

    private String getOrderNo(){

        Random random = new Random(9000000);
        int xx = random.nextInt()+1000000;
        return xx+System.currentTimeMillis()+"";

    }
}
