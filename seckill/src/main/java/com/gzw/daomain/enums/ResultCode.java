package com.gzw.daomain.enums;

/**
 * Created by gujian on 2017/6/23.
 */
public enum ResultCode {
    RESULT_SUCCESS(100,"请求成功"),
    REQUEST_ERROR(000,"请求异常"),
    LOGIN_SUCCESS(100,"登录成功"),
    REGISTER_SUCCESS(100,"注册成功"),
    UPDATE_SUCCESS(100,"修改信息成功"),
    NO_USER(101,"用户不存在"),
    BUY_ERROR(101,"购买失败"),
    ERROR_USERNAME_OR_PASSSWORD(102,"用户名密码错误"),
    USER_EXIST(103,"用户已存在"),
    REGISTER_FAILE(104,"注册失败"),
    RESULT_EMPTY(105,"查找结果为空"),
    TITLE_EMPTY(106,"标题不能为空"),
    CONENT_EMPTY(107,"内容不能为空"),
    UPDATE_ERROR(108,"更新信息失败"),
    STORAGE_ERROR(108,"库存不足"),
    LOGIN_ERROR(109,"登录过期，请重新登录");

    private int resultCode;
    private String message;

    ResultCode(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
