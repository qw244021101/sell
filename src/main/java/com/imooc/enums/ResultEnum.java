package com.imooc.enums;

import lombok.Getter;

/**
 * Created by win10 on 2017/11/23.
 */
@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "订单参数不正确"),
    LOGIN_TIMEOUT(100, "登陆超时，请重新登陆");
    ;
    /** 订单状态码 */
    private Integer code;
    /** 订单状态码对应的信息 */
    private  String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
