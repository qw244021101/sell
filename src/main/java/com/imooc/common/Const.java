package com.imooc.common;

/**
 * Created by lee on 2018/1/9.
 */
public class Const {

    public interface RedisCacheExtime {
        int REDIS_SESSION_EXPIRE = 60 * 30; // 30分钟
    }

    public interface RedisLock {
        String CLOSE_ORDER_TASK_LOCK = "CLOSE_ORDER_TASK_LOCK";
    }
}
