package com.imooc.service;

/**
 *
 */
public interface SeckillService{
    /**
     * 查询秒杀活动特价的商品信息
     */
    String querySecKillProductInfo(String productId);
    /**
     * 秒杀接口，如果秒杀成功会返回剩余的库存两
     */
    void orderProductMockDiffUser(String productId);
}
