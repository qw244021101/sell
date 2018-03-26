package com.imooc.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
@Data
public class ProductVo implements Serializable{
    /** 商品id */
    @JsonProperty("id")
    private String productId;
    /** 商品名称 */
    @JsonProperty("name")
    private String productName;
    /** 商品价格 */
    @JsonProperty("price")
    private BigDecimal productPrice;
    /** 商品介绍 */
    @JsonProperty("description")
    private String productDescription;
    /** 商品图片链接地址 */
    @JsonProperty("icon")
    private String productIcon;

    public ProductVo(){

    }
}
