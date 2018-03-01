package com.leibangzhu.starters.cache.redis;

public interface ICacheManager {

    String get(String key);
    String put(String key, String value);
}
