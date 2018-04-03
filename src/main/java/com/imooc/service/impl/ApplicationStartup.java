package com.imooc.service.impl;

import com.imooc.common.SpringBootContext;
import com.imooc.redis.service.RedisService;
import com.imooc.redis.service.impl.RedisClusterServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent>{

    public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
    }
}
