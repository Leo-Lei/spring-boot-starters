package com.leibangzhu.starters.redis;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface IRedisClient {

    Object get(String key);

    <T> T get(String key, Class<T> clazz);

    void set(String key, Object value);

    void sadd(String key, Object value);

    <T> T spop(String key, Class<T> clazz);

    boolean setNX(String key, String value);

    boolean setNX(String key, String value, int time, TimeUnit unit);

    void delete(String key);

    Set<String> keys(String pattern);

    Long incr(String key,long value);

    void del(Collection<String> keys);
}
