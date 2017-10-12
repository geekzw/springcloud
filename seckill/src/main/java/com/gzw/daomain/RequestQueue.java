package com.gzw.daomain;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gujian on 2017/10/12.
 */
public class RequestQueue {

    private int maxCount;

    private List<HttpServletRequest> queue;

    private Integer comId;

    public RequestQueue() {
        queue = new ArrayList<>();
    }

    public RequestQueue(int maxCount) {
        this.maxCount = maxCount;
        queue = new ArrayList<>();
    }

    public boolean addRequest(HttpServletRequest request){
        if(queue.size()<maxCount){
            queue.add(request);
            return true;
        }
        return false;
    }

    public HttpServletRequest get(){
        if(queue.size()<=0){
            return null;
        }

        return queue.remove(0);
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public Integer getComId() {
        return comId;
    }
}
