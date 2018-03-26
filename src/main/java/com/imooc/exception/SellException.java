package com.imooc.exception;

import com.imooc.enums.OrderExceptionEnum;
import com.imooc.enums.ProductExceptionEnum;
import com.imooc.enums.ResultEnum;

/**
 *
 */
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ProductExceptionEnum productExceptionEnum) {
        super(productExceptionEnum.getMsg());
        this.code = productExceptionEnum.getCode();
    }

    public SellException(OrderExceptionEnum orderExceptionEnum) {
        super(orderExceptionEnum.getMsg());
        this.code = orderExceptionEnum.getCode();
    }
    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(ResultEnum resultEnum, String msg) {
        super(msg);
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
