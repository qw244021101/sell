package com.imooc.viewobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.enums.ResultEnum;

import java.io.Serializable;

/**
 * Created by lee on 2018/1/9.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class JsonVo<T> implements Serializable{
    /** 泛型对象 */
    private T data;
    /** 状态码 */
    private int code;
    /** 提示信息 */
    private String msg;

    public JsonVo(T data) {
        this.setCode(ResultEnum.SUCCESS.getCode());
        this.setMsg("操作成功!");
        this.setData(data);
    }

    public JsonVo(int code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
