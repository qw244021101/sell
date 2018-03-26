package com.imooc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by win10 on 2017/12/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Cluster {

    @Test
    public void testJedisCluster() throws Exception{
        // redis cluster主节点结合
        Set<HostAndPort> nodeList = new HashSet<HostAndPort>();
        nodeList.add(new HostAndPort("192.168.199.131", 7000));
        nodeList.add(new HostAndPort("192.168.199.131", 7001));
        nodeList.add(new HostAndPort("192.168.199.131", 7002));
        // GenericObjectPoolConfig -- 采用默认配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        JedisCluster jedisCluster = new JedisCluster(nodeList, poolConfig);
        jedisCluster.set("php", "java");
    }
}
