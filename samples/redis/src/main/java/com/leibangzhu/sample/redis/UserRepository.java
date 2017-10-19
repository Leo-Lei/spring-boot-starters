package com.leibangzhu.sample.redis;

import com.leibangzhu.starters.redis.IRedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private IRedisClient redisClient;

    public void add(User user){
        redisClient.set(user.getName(),user);
    }

    public User get(String name){
        return redisClient.get(name,User.class);
    }

    @Cacheable(value = "something",key = "#name" )
    public User getByName(String name){
        // 测试@Cache注解
        System.out.println("enter getByName method to get an user...");
        return new User("john",18);
    }

}
