package com.imooc.enums;

import lombok.Getter;

/**
 *
 */
@Getter
public enum OrderExceptionEnum {
    ORDER_NOT_EXISTS(20,"订单不存在"),
    ORDER_DETAIL_NOT_EXISTS(30,"订单详情不存在"),
    ORDER_STATUS_ERROR(40, "订单状态不正确"),
    ORDER_UPDATE_FAILED(50, "订单状态修改失败"),
    ORDER_DETAILS_EMPTY(60, "订单没有选购商品"),
    ORDER_PAY_FAILED(60, "订单支付失败"),
    ORDER_OPENID_ERROR(70, "订单openId错误"),
    ;
    /** 错误码 */
    private Integer code;
    /** 错误信息 */
    private String msg;

    OrderExceptionEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
