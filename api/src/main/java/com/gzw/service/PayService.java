package com.gzw.service;

import com.gzw.dto.PayRequest;
import com.gzw.dto.ResultInfo;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by gujian on 2017/10/10.
 */
public interface PayService {

        @PostMapping(value = "/pay")
        ResultInfo pay(PayRequest request);
}
