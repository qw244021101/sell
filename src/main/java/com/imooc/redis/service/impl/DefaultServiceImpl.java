package com.imooc.redis.service.impl;

import com.imooc.redis.service.DefaultCacheService;
import org.springframework.stereotype.Service;

@Service
public class DefaultServiceImpl extends DefaultCacheService{

    /**
     * 利用抽象类
     */
    public String cache() {
        System.out.println("spark fly");
        return "spark fly";
    }
}
