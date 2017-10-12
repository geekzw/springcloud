package com.gzw.daomain;


import com.alibaba.fastjson.JSON;
import com.gzw.daomain.enums.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by gujian on 2017/6/23.
 */
@Data
public class ResultInfo<T> implements Serializable{

    private int resultCode;

    private boolean isSuccess;

    private String message;

    private T data;

    public static ResultInfo getErrorInfo(ResultCode resultCode){
        ResultInfo resultInfo = new ResultInfo(resultCode.getResultCode(),resultCode.getMessage());
        resultInfo.setSuccess(false);
        return resultInfo;
    }

    public static ResultInfo getSuccessInfo(ResultCode resultCode){
        ResultInfo resultInfo = new ResultInfo(resultCode.getResultCode(),resultCode.getMessage());
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    public static ResultInfo getSuccessInfo(String message){
        ResultInfo resultInfo = new ResultInfo(100,message);
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    public static<T> ResultInfo getSuccessData( T data){
        ResultInfo resultInfo = new ResultInfo(ResultCode.RESULT_SUCCESS.getResultCode(),ResultCode.RESULT_SUCCESS.getMessage(),data);
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    public static ResultInfo getErrorInfo(int code, String message){
        ResultInfo resultInfo = new ResultInfo(code,message);
        resultInfo.setSuccess(false);
        return resultInfo;
    }

    public static ResultInfo getErrorMessage(String message){
        ResultInfo resultInfo = new ResultInfo(00000,message);
        resultInfo.setSuccess(false);
        return resultInfo;
    }

    public static<T> ResultInfo getSuccessWithInfo(ResultCode resultCode, T data){
        ResultInfo resultInfo = new ResultInfo(resultCode.getResultCode(),resultCode.getMessage(),data);
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    public ResultInfo(T data) {
        this.data = data;
    }

    public ResultInfo(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public ResultInfo(int resultCode, String message, T data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public static String getString(ResultInfo resultInfo){
        return JSON.toJSONString(resultInfo);
    }
}
