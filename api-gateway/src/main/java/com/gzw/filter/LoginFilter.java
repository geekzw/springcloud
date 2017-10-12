package com.gzw.filter;

import com.gzw.daomain.Constant;
import com.gzw.daomain.Token;
import com.gzw.daomain.UserIpInfo;
import com.gzw.service.RedisService;
import com.gzw.utils.StringUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gujian on 2017/9/30.
 */
@Slf4j
public class LoginFilter extends ZuulFilter{

    @Autowired
    RedisService service;
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        return !isLogin();
    }

    @Override
    public Object run() {

        check();

        return null;
    }

    private void check() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("token");
        if(StringUtil.isEmpty(token)){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.set(Constant.IS_SUCCESS,false);
            log.error("please login");
            ctx.setResponseBody("please login");
            return;
        }

        Token token1 = service.getObjectValue(token, Token.class);
        if(token1==null){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.set(Constant.IS_SUCCESS,false);
            log.error("Invalid login");
            ctx.setResponseBody("Invalid login");
            return;
        }

        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(200);
        ctx.set(Constant.IS_SUCCESS,true);
    }

    private boolean isLogin() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String string = request.getRequestURI();
        if(string.contains(Constant.LOGIN) || string.contains(Constant.REGISTER)){
            return true;
        }else{
            return false;
        }
    }
}
