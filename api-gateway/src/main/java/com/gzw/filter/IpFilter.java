package com.gzw.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gujian on 2017/9/30.
 */
public class IpFilter extends ZuulFilter{
    private String ip = null;
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String temp_ip = request.getRemoteAddr();
        if(ip == null){
            ip = temp_ip;
            System.out.println("正常转发");
        }else if(ip.equals(temp_ip)){
            System.out.println("请求拦截");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
        }

        return null;
    }
}
