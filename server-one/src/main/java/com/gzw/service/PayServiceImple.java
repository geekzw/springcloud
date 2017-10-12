package com.gzw.service;

import com.gzw.dto.PayRequest;
import com.gzw.dto.ResultInfo;

/**
 * Created by gujian on 2017/10/10.
 */
public class PayServiceImple implements PayService {
    @Override
    public ResultInfo pay(PayRequest request) {
        return new ResultInfo(1,"支付成功");
    }
}
