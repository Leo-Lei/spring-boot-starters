package com.leibangzhu.starters.mybatis.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Properties;

@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MybatisMapperScannerConfiguration {

    // MapperScannerConfigurer这个bean的创建发生在spring初始化容器的比较早的阶段，不支持@ConfigurationProperties和@Value。
    // 暂时使用InputStream直接读取classPath下的application.properties文件中的属性值
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        configurer.setBasePackage(getScanPackage());
        return configurer;
    }

    private String getScanPackage() {
        String scanPackage = "";
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = MybatisMapperScannerConfiguration.class.getResourceAsStream("/application.properties");
            properties.load(inputStream);
            scanPackage  = properties.getProperty(mybatisMapperScanBasePackage).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return scanPackage;
    }

    private final String mybatisMapperScanBasePackage = "spring.mybatis.mapperScanBasePackage";
}
