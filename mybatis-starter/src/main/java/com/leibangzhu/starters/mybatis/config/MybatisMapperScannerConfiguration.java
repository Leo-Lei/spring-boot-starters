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
@EnableConfigurationProperties({MybatisProperties.class})
public class MybatisMapperScannerConfiguration {

    @Autowired
    private MybatisProperties properties;


    // MapperScannerConfigurer这个bean的创建发生在spring初始化容器的比较早的阶段，不支持@ConfigurationProperties和@Value。
    // 下面的这个bean是不能正常工作的，所以该starter暂时不支持在application.properties中配置mybatis的扫描的package。
    // 该Bean暂时没有加入该starter的自动配置中,没有在/src/main/resources/META-INF/spring.factories文件中配置
    // 需要在具体项目的@Configuration中引入@MapperScan注解来实现。
    // 在后续版本中会考虑支持在application.properties中配置mybatis的扫描的package
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(@Value("${spring.mybatis.mapperScanBasePackage}") String mapperScanBasePackage){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        configurer.setBasePackage(properties.getMapperScanBasePackage());
        System.out.println(mapperScanBasePackage);
        return configurer;
    }
}
