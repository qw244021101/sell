package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 *
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Test
    public void testFindOne() throws Exception {
        String productId = "49bda9965da3407792358bba8c9d4dc8";
        ProductInfo productInfo = productServiceImpl.findOne(productId);
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void testFindUpAll() throws Exception {
        List<ProductInfo> productInfos = productServiceImpl.findUpAll();
        Assert.assertNotEquals(0, productInfos.size());
        System.out.println("productInfos:" + productInfos);
    }

    @Test
    public void testFindAll() throws Exception {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> page = productServiceImpl.findAll(pageRequest);
        System.out.println(page);
    }

    @Test
    public void testSave() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(UUID.randomUUID().toString().replace("-",""));
        productInfo.setProductName("Aventador");
        productInfo.setProductPrice(new BigDecimal("738.88"));
        productInfo.setProductStock(9);
        productInfo.setProductDescription("LP700-4");
        productInfo.setProductIcon("");
        productInfo.setProductStatus(ProductEnum.UP.getCode());
        productInfo.setCategoryType(2011);
        ProductInfo product = productServiceImpl.save(productInfo);
        Assert.assertNotNull(product);
    }

    @Test
    public void saveProductInfo(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(UUID.randomUUID().toString().replace("-",""));
        productInfo.setProductName("Aventador");
        productInfo.setProductPrice(new BigDecimal("738.88"));
        productInfo.setProductStock(9);
        productInfo.setProductDescription("LP700-4");
        productInfo.setProductIcon("");
        productInfo.setProductStatus(ProductEnum.UP.getCode());
        productInfo.setCategoryType(2011);
        int num = productServiceImpl.saveProductInfo(productInfo);
        System.out.println("num:" + num);
    }
}