package com.imooc.service.impl;

import com.imooc.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    public void testFindOne() throws Exception {
        ProductCategory productCategory = categoryServiceImpl.findOne(1);
        System.out.println("productCategory:" + productCategory);
    }

    @Test
    public void testFindAll() throws Exception {
        List<ProductCategory> productCategoryList = categoryServiceImpl.findAll();
        System.out.println("productCategoryList:" + productCategoryList);
    }

    @Test
    public void testSave() throws Exception {
//      ProductCategory productCategory = new ProductCategory("Enzo",2002);
//      ProductCategory category = categoryServiceImpl.save(productCategory);
        ProductCategory productCategory = categoryServiceImpl.findOne(1);
        productCategory.setCategoryName("Lego");
        ProductCategory category = categoryServiceImpl.save(productCategory);
        System.out.println("category:" + category);
    }

    @Test
    public void testFindByCategoryTypeIn() throws Exception {
        List<Integer> list = Arrays.asList(21030,2011,2002);
        List<ProductCategory> productCategories = categoryServiceImpl.findByCategoryTypeIn(list);
        System.out.println("productCategories:" + productCategories);
    }
}