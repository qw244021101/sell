package com.imooc.enums;

import lombok.Getter;

/**
 *
 */
@Getter
public enum PayStatusEnum {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功")
    ;

    private Integer coed;
    private String msg;

    PayStatusEnum(Integer coed, String msg) {
        this.coed = coed;
        this.msg = msg;
    }
}
