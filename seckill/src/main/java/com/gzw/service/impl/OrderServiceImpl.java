package com.gzw.service.impl;

import com.gzw.daomain.*;
import com.gzw.daomain.enums.OrderStatus;
import com.gzw.daomain.form.OrderRequest;
import com.gzw.dto.PayRequest;
import com.gzw.mapper.OrderMapper;
import com.gzw.mapper.SecKillMapper;
import com.gzw.service.OrderService;
import com.gzw.service.PayServiceR;
import com.gzw.service.RedisService;
import com.gzw.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

    private ConcurrentLinkedDeque<OrderRequest> catchDeferredResults = new ConcurrentLinkedDeque<>();
    private ConcurrentLinkedDeque<OrderRequest> runningDeferredResults = new ConcurrentLinkedDeque<>();

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    private boolean isScan = false;


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
    public ResultInfo findByUserId(HttpServletRequest request,Integer offset,Integer limit) {

        Token token = redisService.getObjectValue(request.getHeader("token"),Token.class);
        ResultInfo resultInfo;
        resultInfo = ResultInfo.getSuccessData(orderMapper.findByUserId(token.getUserId(),offset,limit));
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

    @Override
    public void addToQueue(OrderRequest orderRequest) {

        String date = DateUtil.getFormatDate(new Date(System.currentTimeMillis()));
        String keyComObj = "seckill:"+orderRequest.getComId();
        String keyComCount = "count:"+orderRequest.getComId();
        ResultInfo resultInfo;

        SecKill secKill = redisService.getObjectValue(keyComObj,SecKill.class);
        if(secKill == null){
            secKill = secKillMapper.getSecKillById(orderRequest.getComId());
            if(secKill == null){
                log.error("数据异常");
                resultInfo = ResultInfo.getErrorMessage("数据异常");
                orderRequest.getResult().setResult(ResultInfo.getString(resultInfo));
                return;
            }
            redisService.setObjectValue(keyComObj,secKill);
            redisService.setStringValue(keyComCount,secKill.getStorageCount()+"");
        }

        if(secKill.getStorageCount().intValue() == -1){
            log.error("库存不足");
            resultInfo = ResultInfo.getErrorMessage("库存不足");
            orderRequest.getResult().setResult(ResultInfo.getString(resultInfo));
            return;
        }

        if(secKill.getStorageCount().intValue()<=0){

            secKill = secKillMapper.getSecKillById(orderRequest.getComId());
            int count = secKill.getStorageCount();
            if(count<=0){
                log.error("库存不足");
                resultInfo = ResultInfo.getErrorMessage("库存不足");
                orderRequest.getResult().setResult(ResultInfo.getString(resultInfo));
                redisService.setStringValue(keyComCount,-1+"");
                return;

            }else{
                redisService.setObjectValue(keyComObj,secKill);
                redisService.setStringValue(keyComCount,secKill.getStorageCount()+"");
            }

        }

        if(secKill.getEndTime().compareTo(date) < 0){
            log.error("秒杀已结束");
            resultInfo = ResultInfo.getErrorMessage("秒杀已结束");
            orderRequest.getResult().setResult(ResultInfo.getString(resultInfo));
            return;
        }

        redisService.decr(keyComCount);

        if(catchDeferredResults == null){
            catchDeferredResults = new ConcurrentLinkedDeque<>();
        }


        catchDeferredResults.add(orderRequest);
        orderRequest.getResult().onCompletion(()->{
            log.info("异步调用完成");
            runningDeferredResults.remove(orderRequest);
        });
        orderRequest.getResult().onTimeout(()->{
            log.error("生成订单超时");
            runningDeferredResults.remove(orderRequest);
            redisService.incr(keyComCount);
        });
    }

    @Scheduled(fixedRate = 200)
    protected void scanCatchRequest(){
        if(!isScan){
            executRequest();
        }

    }

    private void executRequest(){
        isScan = true;
        if(runningDeferredResults == null){
            runningDeferredResults = new ConcurrentLinkedDeque<>();
        }
        for(int i=0;i<catchDeferredResults.size();i++){
            if(runningDeferredResults.size()>=20){
                break;
            }
            log.info("请求添加到线程池");
            OrderRequest request = catchDeferredResults.getFirst();
            executorService.execute(new OrderThread(request.getComId(),request.getRequest(),request.getResult()));
            runningDeferredResults.add(request);
            catchDeferredResults.remove(request);
        }
        isScan = false;
    }

    private String getOrderNo(){

        Random random = new Random(9000000);
        int xx = random.nextInt()+1000000;
        return xx+System.currentTimeMillis()+"";

    }

    private class OrderThread implements Runnable{

        private Integer comId;
        private HttpServletRequest request;
        private DeferredResult<String> result;

        public OrderThread(Integer comId, HttpServletRequest request,DeferredResult<String> result) {
            this.comId = comId;
            this.request = request;
            this.result = result;
        }

        @Override
        public void run() {
            ResultInfo resultInfo = addOrder(comId,request);
            result.setResult(ResultInfo.getString(resultInfo));
        }
    }

}
