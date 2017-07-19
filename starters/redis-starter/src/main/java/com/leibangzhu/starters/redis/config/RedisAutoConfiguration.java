package com.leibangzhu.starters.redis.config;

import com.leibangzhu.starters.redis.IRedisClient;
import com.leibangzhu.starters.redis.RedisClient;
import com.leibangzhu.starters.redis.properties.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties({RedisProperties.class})
@EnableCaching
public class RedisAutoConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setDatabase(redisProperties.getDataBase());
        factory.setHostName(redisProperties.getHost());
        factory.setPort(redisProperties.getPort());
        factory.setPassword(redisProperties.getPassword());
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisProperties.getPool().getMaxIdle());
        poolConfig.setMinIdle(redisProperties.getPool().getMinIdle());
        poolConfig.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
        poolConfig.setMaxTotal(redisProperties.getPool().getMaxActive());
        factory.setPoolConfig(poolConfig);
        factory.setTimeout(redisProperties.getTimeout());
        return factory;
    }

    @Bean(name = "myRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
//		template.setKeySerializer(new StringRedisSerializer());
//		template.setValueSerializer(new JdkSerializationRedisSerializer());
//		template.setValueSerializer(new RedisObjectSerializer());
//		template.afterPropertiesSet();
        return template;
    }

    @Bean
    public IRedisClient redisClient(RedisTemplate<String,Object> redisTemplate){
        return new RedisClient(redisTemplate);
    }

}
