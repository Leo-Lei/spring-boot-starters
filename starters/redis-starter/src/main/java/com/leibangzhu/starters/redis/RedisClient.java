package com.leibangzhu.starters.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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


    public void sadd(String key, Object value){
        redis.opsForSet().add(key,value);
    }

    public <T> T spop(String key,Class<T> clazz){
        return (T)redis.opsForSet().pop(key);
    }

    public boolean setNX(String key,String value){
        return redis.opsForValue().setIfAbsent(key,value);
    }

    public boolean setNX(String key,String value,int time,TimeUnit unit){
        boolean result = redis.opsForValue().setIfAbsent(key,value);
        if (result){
            redis.expire(key,time,unit);
        }
        return result;
    }

    public void delete(String key){
        redis.delete(key);
    }

    public Set<String> keys(String pattern){
        return redis.keys(pattern);
    }

    public Long incr(String key,long value){
        return redis.opsForValue().increment(key,value);
    }

    public void del(Collection<String> keys){
        redis.delete(keys);
    }
}
