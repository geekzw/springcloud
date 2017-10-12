package com.gzw.controller;

import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.SecKill;
import com.gzw.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gujian on 2017/10/11.
 */
@RestController
public class SecKillController {

    @Autowired
    SecKillService secKillService;

    @PostMapping(value = "/addSeckill")
    public String addSeckill(SecKill secKill){
        ResultInfo resultInfo;
        resultInfo = secKillService.addSecKillCom(secKill);
        return resultInfo.getString(resultInfo);
    }

    @PostMapping(value = "/getSeckill")
    public String getSeckill(@RequestParam("id") Integer id){
        ResultInfo resultInfo;
        resultInfo = secKillService.getSecKillById(id);
        return resultInfo.getString(resultInfo);
    }

    @PostMapping(value = "/getSeckillList")
    public String getSeckillList(@RequestParam("offset") Integer offset,@RequestParam("limit") Integer limit){
        ResultInfo resultInfo;
        resultInfo = secKillService.getSecKillList(offset,limit);
        return resultInfo.getString(resultInfo);
    }


}
