package com.gzw.service;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by gujian on 2017/9/30.
 */
@FeignClient(value = "server-one")
public interface UserInfoService extends UserService {

}
