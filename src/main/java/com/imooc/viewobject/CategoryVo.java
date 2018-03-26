package com.imooc.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Data
public class CategoryVo implements Serializable{
    /** 商品类目名称 */
    @JsonProperty("name")
    private String categoryName;
    /** 商品类目类型 */
    @JsonProperty("type")
    private Integer categoryType;
    /** 商品信息 */
    @JsonProperty("foods")
    private List<ProductVo> list;

    public CategoryVo(){

    }
}
