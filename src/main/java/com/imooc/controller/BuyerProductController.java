package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.redis.service.impl.RedisClusterServiceImpl;
import com.imooc.service.impl.CategoryServiceImpl;
import com.imooc.service.impl.ProductServiceImpl;
import com.imooc.viewobject.CategoryVo;
import com.imooc.viewobject.ProductVo;
import com.imooc.viewobject.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private RedisClusterServiceImpl redisClusterService;

    @GetMapping("/list")
    public ResultVo<CategoryVo> list() {
        // 1:查询所有上架的商品
        // 2:查询所有商品类目
        // 一次性全部查出，避免资源的消耗 -- 数据库的查询不能方法for循环里面
        // 先从redis中进行查找，降低数据库的压力
        List<CategoryVo> categoryVos = new ArrayList<>();
        String products = redisClusterService.get("products");
        if (products != null && !"".equalsIgnoreCase(products)) {
            categoryVos = (List<CategoryVo>) JSONObject.parse(products);
            return new ResultVo<CategoryVo>(0, "查询成功", categoryVos);
        }
        List<ProductInfo> productInfos = productService.findUpAll();
        // Java8特性
        List<Integer> categoryTypeList = productInfos.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(categoryTypeList);
        for (ProductCategory productCategory : productCategories) {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(productCategory, categoryVo);
            categoryVos.add(categoryVo);
            List<ProductVo> productVos = new ArrayList<>();
            for (ProductInfo productInfo : productInfos) {
                ProductVo productVo = new ProductVo();
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    BeanUtils.copyProperties(productInfo, productVo);
                    productVos.add(productVo);
                }
            }
            categoryVo.setList(productVos);
        }
        // 向Redis中同步数据
        redisClusterService.set("products", JSONObject.toJSONString(categoryVos));
        return new ResultVo<CategoryVo>(0, "查询成功", categoryVos);
    }
}
