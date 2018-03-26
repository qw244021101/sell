package com.imooc.redis.service.impl;

import com.imooc.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * Created by lee on 2018/1/1.
 */
@Service
public class RedisClusterServiceImpl implements RedisService{

    @Autowired
    private JedisCluster jedisCluster;

    public String get(String key) {
        return jedisCluster.get(key);
    }

    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    public Long delete(String key) {
        return jedisCluster.del(key);
    }
}
