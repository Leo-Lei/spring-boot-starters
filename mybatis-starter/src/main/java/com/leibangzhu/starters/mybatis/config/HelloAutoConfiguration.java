package com.leibangzhu.starters.mybatis.config;

import com.leibangzhu.starters.mybatis.properties.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({MybatisProperties.class})
public class HelloAutoConfiguration {

    @Autowired
    private MybatisProperties properties;

    @Bean
    public Mybatis mybatis(){
        Mybatis mybatis = new Mybatis();
        String s = properties.getMapperScanBasePackage();
        System.out.println(s);
        return mybatis;
    }
}
