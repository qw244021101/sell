package com.imooc.redis.service;

/**
 * Created by lee on 2018/1/1.
 */
public interface RedisService {
    /** 根据key值获取指定的value */
    public String get(String key);
    /** 向redis中存储键值对 */
    public String set(String key, String value);
    /** 删除redis中的键值对 */
    public Long delete(String key);

}
