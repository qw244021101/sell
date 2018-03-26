package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品信息接口
 */
@Mapper
public interface ProductService {
    /**
     * 根据商品id查询商品信息
     */
    ProductInfo findOne(String productId);
    /**
     * 根据商品状态查询上架商品信息
     */
    List<ProductInfo> findUpAll();
    /**
     * 查询所有商品信息(分页查询)
     */
    Page<ProductInfo> findAll(Pageable pageable);
    /**
     * 保存商品
     */
    ProductInfo save(ProductInfo productInfo);
    /**
     * 根据商品类目查询商品
     */
    List<ProductInfo> findByCategoryType(Integer categoryType);
    /**
     * 减库存
     */
    void decreaseStock(List<CartDto> cartDtos);
    /**
     * 加库存
     */
    void increaseStock(List<CartDto> cartDtos);
}
