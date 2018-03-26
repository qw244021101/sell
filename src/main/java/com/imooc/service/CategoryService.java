package com.imooc.service;

import com.imooc.dataobject.ProductCategory;

import java.util.List;

/**
 * 商品类目接口
 */
public interface CategoryService {
    /**
     * 根据id查询商品类目
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有商品类目
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 更新或保存商品类目
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);

    /**
     * 根据类型查询商品类目
     * @param categoryList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);

}
