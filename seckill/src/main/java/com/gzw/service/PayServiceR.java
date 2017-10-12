package com.gzw.service;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by gujian on 2017/10/12.
 */
@FeignClient("server-one")
public interface PayServiceR  extends PayService{
}
