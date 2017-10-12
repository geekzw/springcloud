package com.gzw.controller;

import com.gzw.dto.PayRequest;
import com.gzw.dto.ResultInfo;
import com.gzw.service.PayService;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gujian on 2017/10/10.
 */
@RestController
public class PayController implements PayService {
    @Override
    public ResultInfo pay(PayRequest request) {
        return new ResultInfo(1,"支付成功");
    }
}
