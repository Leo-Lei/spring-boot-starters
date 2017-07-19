package com.leibangzhu.starters.redis;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisClient implements IRedisClient {

    private RedisTemplate<String,Object> redis;

    public RedisClient(RedisTemplate<String,Object> redis){
        this.redis = redis;
    }

    @Override
    public Object get(String key) {
        return redis.opsForValue().get(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T)get(key);
    }

    @Override
    public void set(String key, Object value) {
        redis.opsForValue().set(key,value);
    }
}
