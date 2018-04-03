package com.imooc.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lee on 2018/1/1.
 */
@Slf4j
@Configuration
public class RedisConfig {
    /** Redis集群节点 */
    private List<String> hostPortList;
    /** 超时时间(毫秒) */
    private int timeout;
    /** Redis连接 */
    private JedisCluster jedisCluster;

    @Autowired
    private ClusterConfigurationProperties clusterConfigurationProperties;

    private Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public JedisCluster jedisCluster(){
        Set<HostAndPort> nodeSet = new HashSet<>();
        // Redis集群参数配置
        timeout = clusterConfigurationProperties.getTimeout();
        hostPortList = clusterConfigurationProperties.getNodes();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 获取Redis集群连接
        for (String hostPort : hostPortList) {
            String[] arr = hostPort.split(":");
            if (arr.length != 2) {
                continue;
            }
            nodeSet.add(new HostAndPort(arr[0], Integer.parseInt(arr[1])));
        }
        try {
            jedisCluster = new JedisCluster(nodeSet, timeout, jedisPoolConfig);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
        }
        return jedisCluster;
    }

    @Value("${spring.redis.ip}")
    private String redisIp;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Bean
    public Redisson redisson() {
        try {
            Config config = new Config();
            config.useSingleServer().setAddress(new StringBuilder().append(redisIp).append(":").append(redisPort).toString());
            Redisson redisson = (Redisson)Redisson.create(config);
            return redisson;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("init redisson error");
        }
        return null;
    }
}
