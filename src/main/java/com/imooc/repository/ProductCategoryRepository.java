package com.imooc.repository;

import com.imooc.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JpaRepository需给出实体类和主键类型
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{
    /**
     * 根据商品类型查询商品类目
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);
}
