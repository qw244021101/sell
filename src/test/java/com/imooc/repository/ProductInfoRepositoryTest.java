package com.imooc.repository;

import com.imooc.dataobject.ProductInfo;
import com.imooc.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void testSave(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(UUID.randomUUID().toString().replace("-",""));
        productInfo.setProductName("Lego21030");
        productInfo.setProductPrice(new BigDecimal("1099"));
        productInfo.setProductStock(23);
        productInfo.setProductDescription("Lego");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(21030);
        ProductInfo product = productInfoRepository.save(productInfo);
        Assert.assertNotNull(product);
    }

    @Test
    public void testFindByCategoryType() throws Exception {
        List<ProductInfo> productInfos = productInfoRepository.findByCategoryType(21030);
        System.out.println("productInfos:" + productInfos);
    }

    @Test
    public void testFindByProductStatus() throws Exception {
        List<ProductInfo> productInfos = productInfoRepository.findByProductStatus(0);
        System.out.println("productInfos:" + productInfos);
    }
}