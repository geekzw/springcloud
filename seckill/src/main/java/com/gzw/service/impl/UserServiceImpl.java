package com.gzw.service.impl;

import com.gzw.daomain.ResultInfo;
import com.gzw.daomain.Token;
import com.gzw.daomain.User;
import com.gzw.daomain.enums.ResultCode;
import com.gzw.mapper.UserMapper;
import com.gzw.service.RedisTokenService;
import com.gzw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gujian on 2017/10/11.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTokenService tokenService;

    @Override
    public ResultInfo login(User user) {
        ResultInfo resultInfo;

        User tempUser = userMapper.findByName(user.getUsername());
        if(tempUser == null){
            log.error(ResultCode.NO_USER.getMessage());
            resultInfo = ResultInfo.getErrorInfo(ResultCode.NO_USER);
            return resultInfo;
        }

        if(!tempUser.getPassword().equals(user.getPassword())){
            log.error(ResultCode.ERROR_USERNAME_OR_PASSSWORD.getMessage());
            resultInfo = ResultInfo.getErrorInfo(ResultCode.ERROR_USERNAME_OR_PASSSWORD);
            return resultInfo;
        }

        Token token = tokenService.create(tempUser);
        resultInfo = ResultInfo.getSuccessWithInfo(ResultCode.LOGIN_SUCCESS,token.getToken());
        return resultInfo;

    }

    @Override
    public ResultInfo getUser(String token) {
        ResultInfo resultInfo;

        Token tompToken = tokenService.getTokenByToken(token);
        User tempUser = userMapper.findByName(tompToken.getUsername());
        if(tempUser == null){
            log.error("查询用户信息失败");
            resultInfo = ResultInfo.getErrorMessage("查询用户信息失败");
        }else{
            resultInfo = ResultInfo.getSuccessData(tempUser);
        }
        return resultInfo;
    }

    @Override
    public ResultInfo register(User user) {
        ResultInfo resultInfo;

        User tempUser = userMapper.findByName(user.getUsername());
        if(tempUser!=null){
            resultInfo = ResultInfo.getErrorInfo(ResultCode.USER_EXIST);
            return resultInfo;
        }

        int index = userMapper.register(user.getUsername(),user.getPassword());
        if(index > 0){
            resultInfo = ResultInfo.getSuccessInfo(ResultCode.REGISTER_SUCCESS);
        }else{
            log.error(ResultCode.REGISTER_FAILE.getMessage());
            resultInfo = ResultInfo.getSuccessInfo(ResultCode.REGISTER_FAILE);
        }
        return resultInfo;
    }
}
