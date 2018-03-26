package com.imooc.repository;

import com.imooc.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品信息接口
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String>{
    /**
     * 根据商品类目查询商品
     */
    List<ProductInfo> findByCategoryType(Integer categoryType);
    /**
     * 根据商品状态查询商品
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

}
