package com.leibangzhu.starters.redis.config;

import com.leibangzhu.starters.redis.IRedisClient;
import com.leibangzhu.starters.redis.RedisClient;
import com.leibangzhu.starters.redis.properties.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class RedisAutoConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig config = jedisPoolConfig();
        JedisPool pool = new JedisPool(
                config,
                redisProperties.getHost(),
                redisProperties.getPort(),
                redisProperties.getTimeout(),
                redisProperties.getPassword());
        return pool;
    }

    @Bean
    public IRedisClient redisClient(JedisPool pool){
        return new RedisClient(pool);
    }
}
