package com.imooc.dataobject.mapper;

import com.imooc.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.KeyUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoMapperTest {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Test
    public void testFindProductInfo() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductStatus(0);
        List<ProductInfo> productInfos = productInfoMapper.findProductInfo(productInfo);
        Assert.assertNotEquals(0, productInfos.size());
    }

    @Test
    public void testSaveProductInfo() throws Exception {
//        Map<String, Object> map = new HashMap<>();
//        map.put("product_id", KeyUtil.getUniqueKey());
//        map.put("product_name", "Reventon");
//        map.put("product_price", new BigDecimal("738.88"));
//        map.put("product_stock", 3);
//        map.put("category_type", 2010);
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(KeyUtil.getUniqueKey());
        productInfo.setProductName("Reventon");
        productInfo.setProductPrice(new BigDecimal("738.88"));
        productInfo.setProductStock(3);
        productInfo.setCategoryType(2010);
        productInfoMapper.saveProductInfo(productInfo);
    }

    @Test
    public void testUpdateProductInfo() throws Exception {
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setProductDescription("Lamborghini");
//        productInfo.setProductStatus(1);
//        productInfoMapper.updateProductInfo(productInfo);
        productInfoMapper.updateProductInfo("Lamborghini", 1);
    }

    @Test
    public void testDeleteProductInfo() throws Exception {
        productInfoMapper.deleteProductInfo("1511603159576826944");
    }

    @Test
    public void testSelectByProductStatus() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductStatus(0);
        List<ProductInfo> list = productInfoMapper.selectByProductStatus(productInfo);
        Assert.assertNotNull(list);
    }
}