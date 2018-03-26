package com.imooc.viewobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVo<T> implements Serializable{
    /** 错误码 */
    @JsonProperty("code")
    private Integer code;
    /** 提示信息 */
    @JsonProperty("msg")
    private String msg;
    /** 具体内容 */
    @JsonProperty("data")
    private List<T> data;
    /** 具体内容 */
    @JsonProperty("dataInfo")
    private Map<T, T> map;

    public ResultVo(Integer code, String msg, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVo(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ResultVo(Integer code, String msg, Map map) {
        this.code = code;
        this.msg = msg;
        this.map = map;
    }
}
