package com.imooc.handler;

import com.imooc.exception.ResponseException;
import com.imooc.exception.SellException;
import com.imooc.viewobject.ResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 全局异常处理机制
 */
@RestControllerAdvice
public class SellExceptionHandler {

    @ExceptionHandler(value = {SellException.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResultVo handlerSellException(SellException e) {
        return new ResultVo(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = ResponseException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handlerResponseException(){

    }
}
