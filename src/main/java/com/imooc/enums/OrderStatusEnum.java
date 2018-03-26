package com.imooc.enums;

import lombok.Getter;

/**
 *
 */
@Getter
public enum OrderStatusEnum {
    NEW(0, "接收新订单"),
    FINISH(1, "订单已完成"),
    CANCEL(2, "订单已取消"),
    ;

    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
