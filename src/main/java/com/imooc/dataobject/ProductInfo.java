package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 *
 */
@Entity
@DynamicUpdate
@Data
public class ProductInfo {
    /** 商品id */
    @Id
    private String productId;
    /** 商品名称 */
    private String productName;
    /** 商品单价 */
    private BigDecimal productPrice;
    /** 商品库存 */
    private Integer productStock;
    /** 商品描述 */
    private String productDescription;
    /** 商品图片链接 */
    private String productIcon;
    /** 商品在线状态 */
    private Integer productStatus;
    /** 所属商品类目 */
    private Integer categoryType;
    /** 默认构造器 */
    public ProductInfo(){

    }

    public String toString() {
        return "ProductInfo{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productStock=" + productStock +
                ", productDescription='" + productDescription + '\'' +
                ", productIcon='" + productIcon + '\'' +
                ", productStatus=" + productStatus +
                ", categoryType=" + categoryType +
                '}';
    }
}
