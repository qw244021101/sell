package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    /* 商品类目id */
    @Id
    @GeneratedValue
    private Integer categoryId;

    /* 商品类目名称 */
    private String categoryName;

    /* 商品类目编号 */
    private Integer categoryType;

    public ProductCategory(){

    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
