package com.leibangzhu.starters.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisClient implements IRedisClient {
    @Override
    public String get(String key) {
        try(Jedis jedis = pool.getResource() ){
            return jedis.get(key);
        }
    }

    @Override
    public String set(String key, String value) {
        try(Jedis jedis = pool.getResource() ){
            return jedis.set(key,value);
        }
    }

    private JedisPool pool;

    public RedisClient(JedisPool pool){
        this.pool = pool;
    }
}
