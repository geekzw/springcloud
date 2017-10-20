package com.gzw.controller;

import com.gzw.daomain.Constants;
import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.SecKill;
import com.gzw.daomain.form.SecKillListCondition;
import com.gzw.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gujian on 2017/10/11.
 */
@RestController
@RequestMapping(value = "/secKills")
public class SecKillController {

    @Autowired
    SecKillService secKillService;

    @PostMapping
    public String addSecKill(@RequestBody  SecKill secKill){
        ResultInfo resultInfo;
        resultInfo = secKillService.addSecKillCom(secKill);
        return resultInfo.getString(resultInfo);
    }

    @GetMapping(value = "/{id}")
    public String getSecKill(@PathVariable("id") Integer id){
        ResultInfo resultInfo;
        resultInfo = secKillService.getSecKillById(id);
        return resultInfo.getString(resultInfo);
    }

    @GetMapping
    public String getSecKillList(@RequestParam(value = "offset",defaultValue = "0") Integer offset,
                                 @RequestParam(value = "limit",defaultValue = Constants.PAGE_LIMIT) Integer limit){
        ResultInfo resultInfo;
        resultInfo = secKillService.getSecKillList(offset,limit);
        return resultInfo.getString(resultInfo);
    }


}
