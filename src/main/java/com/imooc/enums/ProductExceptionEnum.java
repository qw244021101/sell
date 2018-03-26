package com.imooc.enums;

import lombok.Getter;

/**
 *
 */
@Getter
public enum ProductExceptionEnum {
    PRODUCT_NOT_EXISTS(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(20,"商品库存不足"),
    PRODUCT_NOT_ACTIVE(30,"商品已下架"),
    SECKILL_PRODUCT_FAILED(30,"商品秒杀失败")
    ;

    /** 错误码 */
    private Integer code;
    /** 错误信息 */
    private String msg;

    ProductExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
