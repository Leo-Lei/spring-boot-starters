package com.leibangzhu.starters.mongo.config;

import com.leibangzhu.starters.mongo.IMongoClient;
import com.leibangzhu.starters.mongo.MongoClient;
import com.leibangzhu.starters.mongo.properties.MongoProperties;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableConfigurationProperties({MongoProperties.class})
public class MongoAutoConfiguration {

    @Autowired
    private MongoProperties mongoProperties;

    @Bean
    public com.mongodb.MongoClient mongo(){
        String host     = mongoProperties.getHost();
        int    port     = mongoProperties.getPort();
        String db       = mongoProperties.getDb();
        String user     = mongoProperties.getUser();
        String password = mongoProperties.getPassword();
        // 设置用户名，密码
        MongoCredential credential = MongoCredential.createCredential(user,db,password.toCharArray());
        // 在这里添加额外的连接参数
        MongoClientOptions options = MongoClientOptions.builder().build();
        // 创建MongoClient
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(
                new ServerAddress(host,port),
                Arrays.asList(credential),
                options
        );
        return mongoClient;
    }

    @Bean
    public IMongoClient mongoClient(com.mongodb.MongoClient mongo){
        return new MongoClient(mongo);
    }
}
