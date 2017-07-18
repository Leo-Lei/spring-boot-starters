package com.leibangzhu.starters.redis;

public interface IRedisClient {

    Object get(String key);
    <T> T get(String key, Class<T> clazz);
    void set(String key, Object value);
}
