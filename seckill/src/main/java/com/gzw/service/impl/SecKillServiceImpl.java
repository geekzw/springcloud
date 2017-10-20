package com.gzw.service.impl;

import com.gzw.daomain.Constants;
import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.SecKill;
import com.gzw.mapper.SecKillMapper;
import com.gzw.service.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by gujian on 2017/10/11.
 */
@Service
@Slf4j
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    SecKillMapper secKillMapper;

    @Override
    public ResultInfo addSecKillCom(SecKill secKill) {
        ResultInfo resultInfo;
        int index = secKillMapper.addSecKillCom(secKill);

        if(index > 0){
            resultInfo = ResultInfo.getSuccessInfo("添加成功");
        }else{
            log.error("添加秒杀商品失败");
            resultInfo = ResultInfo.getErrorMessage("添加失败");
        }

        return resultInfo;
    }

    @Override
    public ResultInfo getSecKillList(int offset, int limit) {
        ResultInfo resultInfo;
        resultInfo = ResultInfo.getSuccessData(secKillMapper.getSecKills(offset,limit));

        return resultInfo;
    }

    @Override
    public ResultInfo getSecKillById(Integer id) {

        ResultInfo resultInfo;
        SecKill secKill = secKillMapper.getSecKillById(id);
        if(secKill == null){
            resultInfo = ResultInfo.getSuccessInfo("没有这个数据");
        }else{
            resultInfo = ResultInfo.getSuccessData(secKill);
        }

        return resultInfo;
    }

    @Override
    public ResultInfo inCrementStorage(Integer id,String date) {

        ResultInfo resultInfo;

        int index = secKillMapper.inCrementStorage(id,date);

        if(index > 0){
            resultInfo = ResultInfo.getSuccessInfo("库存操作成功");
            resultInfo.setSuccess(true);
        }else{
            log.error("库存操作失败");
            resultInfo = ResultInfo.getErrorMessage("库存操作失败");
            resultInfo.setSuccess(false);
        }

        return resultInfo;
    }
}
