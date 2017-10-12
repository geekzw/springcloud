package com.gzw.service;

import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.SecKill;

import java.util.Date;

/**
 * Created by gujian on 2017/10/11.
 */
public interface SecKillService {

    ResultInfo addSecKillCom(SecKill secKill);

    ResultInfo getSecKillList(int offset,int limit);

    ResultInfo getSecKillById(Integer id);

    ResultInfo inCrementStorage(Integer id,String date);

}
