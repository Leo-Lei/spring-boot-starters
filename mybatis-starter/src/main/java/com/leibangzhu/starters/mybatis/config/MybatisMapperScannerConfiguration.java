package com.leibangzhu.starters.mybatis.config;

import com.leibangzhu.starters.mybatis.properties.MybatisProperties;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
//@EnableConfigurationProperties({MybatisProperties.class})
public class MybatisMapperScannerConfiguration {

//    @Autowired
//    private MybatisProperties properties;

    @Bean
    public Mybatis mybatis(@Value("${spring.mybatis.mapperScanBasePackage}") String mapperScanBasePackage){
        Mybatis configurer = new Mybatis();
        //configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //configurer.setBasePackage(properties.getMapperScanBasePackage());
        System.out.println(mapperScanBasePackage);
        return configurer;
    }


//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer(@Value("${spring.mybatis.mapperScanBasePackage}") String mapperScanBasePackage){
//        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//        //configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        //configurer.setBasePackage(properties.getMapperScanBasePackage());
//        System.out.println(mapperScanBasePackage);
//        return configurer;
//    }
}
