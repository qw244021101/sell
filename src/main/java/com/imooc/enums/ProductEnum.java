package com.imooc.enums;

import lombok.Getter;

/**
 * 商品信息枚举类
 */
@Getter
public enum ProductEnum {
    UP  (0, "商品处于热销"),
    DOWN(1, "商品已经下架");

    /** 商品状态码 */
    private Integer code;
    /** 商品状态码对应的信息 */
    private  String message;

    ProductEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
