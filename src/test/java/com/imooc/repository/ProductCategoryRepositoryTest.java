package com.imooc.repository;

import com.imooc.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void test(){
        ProductCategory productCategory = productCategoryRepository.findOne(1);
        System.out.println("ProductCategory:" + productCategory);
    }

    @Test
    @Transactional
    // 测试加此注解在方法完成后全部回滚
    public void testSave(){
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setCategoryName("Aventador");
//        productCategory.setCategoryType(2011);
//        productCategoryRepository.save(productCategory);
        ProductCategory productCategory = new ProductCategory("Enzo",2002);
        ProductCategory product = productCategoryRepository.save(productCategory);
        Assert.assertNotNull(product);
//      Assert.assertNotEquals(null, product);
        log.info("商品类目保存成功...");
    }

    @Test
    public void testUpdate(){
        ProductCategory productCategory = productCategoryRepository.findOne(1);
        productCategory.setCategoryId(1);
        productCategory.setCategoryName("LeGo");
        productCategoryRepository.save(productCategory);
        log.info("商品类目更新成功...");
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> categoryList = Arrays.asList(2011,21030,2017);
        List<ProductCategory> productCategories = productCategoryRepository.findByCategoryTypeIn(categoryList);
        System.out.println("productCategories:" + productCategories);
        log.info("查询成功...");
    }
}