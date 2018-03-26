package com.imooc.service.impl;

import com.imooc.common.Const;
import com.imooc.enums.ProductExceptionEnum;
import com.imooc.exception.SellException;
import com.imooc.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;
import util.KeyUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Slf4j
@Service
public class SecKillServiceImpl implements SeckillService{

    /** 超时时间 */
    private static final int TIME_OUT = 1000 * 5;

    @Autowired
    private Redisson redisson;

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 活动特价，限时限量100000
     */
    static Map<String,Integer> products;
    static Map<String,Integer> stock;
    static Map<String,String> orders;
    static {
        products = new HashMap<>();
        stock    = new HashMap<>();
        orders   = new HashMap<>();
        products.put("244021101",100000);
        stock.put("244021101",100000);
    }

    private String queryMap(String productId){
        return "乐高积木活动特价限时限量" + products.get(productId) + "，剩余" + stock.get(productId) + "个，成功下订单用户数:" + orders.size() + "人...";
    }

    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    public void orderProductMockDiffUser(String productId) {
        // 加锁
        long value = System.currentTimeMillis() + TIME_OUT;
        if (!redisLock.lock(productId, String.valueOf(value))) {
            throw new SellException(ProductExceptionEnum.SECKILL_PRODUCT_FAILED);
        }
        // 1、查询该商品库存，为0则结束活动
        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SellException(ProductExceptionEnum.PRODUCT_NOT_ACTIVE);
        }
        // 2、下单
        orders.put(KeyUtil.getUniqueKey(), productId);
        // 3、减库存
        stockNum = stockNum - 1;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("");
        }
        stock.put(productId, stockNum);
        // 解锁
        redisLock.unlock(productId, String.valueOf(value));
    }

//    @Scheduled(cron = "0 */1 * * * ?")
//    public void orderProductMockDiffUser() {
//        long timeout = Integer.parseInt(PropertiesUtil.getPorperty("lock.timeout", "5000"));
//        Long result = jedisCluster.setnx(Const.RedisLock.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + timeout));
//        if (result != null && result == 1) {
//            // get redisson lock
//            closeOrder(Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
//        } else {
//            // 未能获取到redisson并发锁，继续判断时间戳，是否可以重置并获取到锁
//            String lockStr = jedisCluster.get(Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
//            if (lockStr != null && System.currentTimeMillis() > Long.parseLong(lockStr)) {
//                // get redisson lock
//                String value = jedisCluster.getSet(Const.RedisLock.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + timeout));
//                if (value == null || (value != null) && StringUtils.equals(value, lockStr)) {
//                    closeOrder(Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
//                } else {
//                    log.info("does not get redisson lock:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
//                }
//            } else {
//                log.info("do not get redisson lock:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
//            }
//        }
//        log.info("close scheduled task success");
//    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void orderProductMockDiffUser() {
        RLock lock = redisson.getLock(Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
        boolean locked = false;
        try {
            if (locked = lock.tryLock(0, 50, TimeUnit.SECONDS)) {
                log.info("get redisson lock:{}, threadName:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
                // -----------业务逻辑(cancel order start)-----------
                // -----------业务逻辑(cancel order end)-------------
                Thread.sleep(1000);
            } else {
                log.info("does get redisson lock:{}, threadName:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("get redisson lock error", e);
        } finally {
            if (!locked) {
                return;
            }
            lock.unlock();
        }
    }

    public void closeOrder(String lockName) {
        jedisCluster.expire(lockName, 5);
        log.info("get redisson lock:{}, threadName:{}", lockName, Thread.currentThread().getName());
        // -----------业务逻辑(cancel order start)-----------
        // -----------业务逻辑(cancel order end)-----------
        jedisCluster.del(lockName);
        log.info("release redisson lock:{}, threadName:{}", lockName, Thread.currentThread().getName());
    }
}
