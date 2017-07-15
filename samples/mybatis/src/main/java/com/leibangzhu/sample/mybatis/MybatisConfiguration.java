package com.leibangzhu.sample.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.leibangzhu.sample.mybatis.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfiguration {
}
