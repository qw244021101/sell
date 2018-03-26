package com.imooc.dataobject.dao;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dataobject.mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by win10 on 2017/11/25.
 */
@Repository
public class ProductInfoDao {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    public List<ProductInfo> findProductInfo(ProductInfo productInfo) {
        return productInfoMapper.findProductInfo(productInfo);
    }

    public int saveProductInfo(ProductInfo productInfo) {
        return productInfoMapper.saveProductInfo(productInfo);
    }
}
