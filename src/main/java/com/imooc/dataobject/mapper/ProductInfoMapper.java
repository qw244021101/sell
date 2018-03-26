package com.imooc.dataobject.mapper;

import com.imooc.dataobject.ProductInfo;
import org.apache.ibatis.annotations.*;


import java.util.List;

/**
 *
 */
@Mapper
public interface ProductInfoMapper {

    @Select("select product_name, product_price, product_stock from product_info where product_status=#{productStatus, jdbcType=INTEGER}")
    @Results({
            @Result(column = "product_name" ,property = "productName"),
            @Result(column = "product_price" ,property = "productPrice"),
            @Result(column = "product_stock" ,property = "productStock")
    })
    List<ProductInfo> findProductInfo(ProductInfo productInfo);

//    @Insert("insert into product_info(product_id, product_name, product_price, product_stock, category_type) values (#{product_id, jdbcType=VARCHAR}, #{product_name, jdbcType=VARCHAR}, #{product_price, jdbcType=DECIMAL}, #{product_stock, jdbcType=INTEGER}, #{category_type, jdbcType=INTEGER})")
//    int saveProductInfo(Map<String, Object> map);

    @Insert("insert into product_info(product_id, product_name, product_price, product_stock, category_type) values (#{productId, jdbcType=VARCHAR}, #{productName, jdbcType=VARCHAR}, #{productPrice, jdbcType=DECIMAL}, #{productStock, jdbcType=INTEGER}, #{categoryType, jdbcType=INTEGER})")
    int saveProductInfo(ProductInfo productInfo);

//    @Update("update product_info set product_description = #{productDescription, jdbcType=VARCHAR} where product_status = #{productStatus, jdbcType=INTEGER}")
//    int updateProductInfo(ProductInfo productInfo);

    @Update("update product_info set product_description = #{productDescription, jdbcType=VARCHAR} where product_status = #{productStatus, jdbcType=INTEGER}")
    int updateProductInfo(@Param("productDescription") String productDescription,
                          @Param("productStatus") Integer productStatus);

    @Delete("delete from product_info where product_id = #{productId}")
    int deleteProductInfo(@Param("productId") String productId);

    List<ProductInfo> selectByProductStatus(ProductInfo productInfo);
}
