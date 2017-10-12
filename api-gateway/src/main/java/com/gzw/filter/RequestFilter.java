package com.gzw.filter;

import com.gzw.daomain.Constant;
import com.gzw.daomain.Token;
import com.gzw.daomain.UserIpInfo;
import com.gzw.service.RedisService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gujian on 2017/9/30.
 */
@Slf4j
public class RequestFilter extends ZuulFilter{

    @Autowired
    RedisService service;
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 6;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().getBoolean(Constant.IS_SUCCESS)&&isKill();
    }

    @Override
    public Object run() {

        check();

        return null;
    }

    private void check() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String temp_ip = request.getRemoteAddr();
        long curTime = System.currentTimeMillis();
        String token = request.getHeader("token");
        Token token1 = service.getObjectValue(token, Token.class);
        String key = getKey(token1);
        UserIpInfo userIpInfo = service.getObjectValue(key,UserIpInfo.class);
        if(userIpInfo == null){
            userIpInfo = new UserIpInfo(token,temp_ip,curTime);
            service.setObjectValue(key,userIpInfo);
        }else{
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            log.error("seckilling please waite");
            ctx.setResponseBody("seckilling please waite");
//            long lastTime = userIpInfo.getRequestTime() == null?0:userIpInfo.getRequestTime();
//            if(curTime - lastTime < Constant.REQUEST_TIME){
//
//                ctx.setSendZuulResponse(false);
//                ctx.setResponseStatusCode(401);
//                ctx.setResponseBody("request frequently");
//            }
//            userIpInfo.setRequestTime(curTime);
//            service.setObjectValue(key,userIpInfo);


        }
    }

    private String getKey(Token token1) {

        return "seckill:"+token1.getUsername();
    }

    private boolean isKill() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String string = request.getRequestURI();
        if(string.contains(Constant.KILL)){
            return true;
        }else{
            return false;
        }
    }
}
