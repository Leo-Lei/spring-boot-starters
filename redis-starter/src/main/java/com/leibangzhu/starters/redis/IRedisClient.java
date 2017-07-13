package com.leibangzhu.starters.redis;

public interface IRedisClient {
    String get(String key);
    String set(String key, String value);
}
