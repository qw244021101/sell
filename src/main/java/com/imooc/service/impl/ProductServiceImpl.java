package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dataobject.dao.ProductInfoDao;
import com.imooc.dto.CartDto;
import com.imooc.enums.ProductEnum;
import com.imooc.enums.ProductExceptionEnum;
import com.imooc.exception.SellException;
import com.imooc.redis.service.impl.RedisClusterServiceImpl;
import com.imooc.repository.ProductCategoryRepository;
import com.imooc.repository.ProductInfoRepository;
import com.imooc.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品信息接口实现类
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private RedisClusterServiceImpl redisClusterService;

    @Autowired
    private ProductInfoDao productInfoDao;

    public ProductInfo findOne(String productId) {
        return productInfoRepository.findOne(productId);
    }

    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductEnum.UP.getCode());
    }

    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    public ProductInfo save(ProductInfo productInfo) {
        redisClusterService.delete("products");
        return productInfoRepository.save(productInfo);
    }

    @Transactional
    public int saveProductInfo(ProductInfo productInfo) {
        return productInfoDao.saveProductInfo(productInfo);
    }

    public List<ProductInfo> findByCategoryType(Integer categoryType) {return productInfoRepository.findByCategoryType(categoryType);}

    @Transactional
    public void decreaseStock(List<CartDto> cartDtos) {
        for (CartDto cartDto : cartDtos){
            ProductInfo productInfo = productInfoRepository.findOne(cartDto.getProductId());
            if (productInfo == null){
                throw  new SellException(ProductExceptionEnum.PRODUCT_NOT_EXISTS);
            }
            Integer stock = productInfo.getProductStock();
            if(stock - cartDto.getProductQuantity() < 0){
                throw new SellException(ProductExceptionEnum.PRODUCT_STOCK_ERROR);
            }
            Integer stockLeft = stock - cartDto.getProductQuantity();
            productInfo.setProductStock(stockLeft);
            productInfoRepository.save(productInfo);
        }
    }

    /**
     *  返还库存
     * @param cartDtos
     */
    @Transactional
    public void increaseStock(List<CartDto> cartDtos) {
        for (CartDto cartDto : cartDtos){
            ProductInfo productInfo = productInfoRepository.findOne(cartDto.getProductId());
            if (productInfo == null){
                throw new SellException(ProductExceptionEnum.PRODUCT_NOT_EXISTS);
            }
            Integer stock = productInfo.getProductStock();
            stock = stock + cartDto.getProductQuantity();
            productInfo.setProductStock(stock);
            productInfoRepository.save(productInfo);
        }
    }
}
