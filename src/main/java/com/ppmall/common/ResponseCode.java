package com.ppmall.common;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.common
 * 2019/1/15
 */
public enum  ResponseCode {

    /**
     * SUCCESS 登录成功
     * ERROR 登录失败
     * NEED_LOGIN 需要登录
     * ILLEGAL_ARGUMENT 参数异常
     */
    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_AUGMENT");

    private final int code;
    private final String desc;



    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
