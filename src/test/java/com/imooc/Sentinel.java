package com.imooc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by win10 on 2018/1/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Sentinel {

    private Logger logger = LoggerFactory.getLogger(Sentinel.class);

    @Test
    public void redisSentinelFailoverTest() {
        String masterName = "mymaster";
        Set<String> sentinels = new HashSet<String>();
        sentinels.add("192.168.199.131:26379");
        sentinels.add("192.168.199.131:26380");
        sentinels.add("192.168.199.131:26381");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels);
        int i = 0;
        while(true){
            Jedis jedis = null;
            try{
                jedis = jedisSentinelPool.getResource();
                String key = "lee" + i;
                String value = "yangdanjie" + i;
                jedis.set(key, value);
                logger.info("{} value is {}", key, jedis.get(key));
                TimeUnit.SECONDS.sleep(1);
                i ++;
            }catch(Exception e){
                logger.error(e.getMessage(), e);
            }finally{
                if(jedis != null){
                    jedis.close();
                }
            }
        }
    }
}
