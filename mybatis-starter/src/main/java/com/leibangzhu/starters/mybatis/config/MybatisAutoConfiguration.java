package com.leibangzhu.starters.mybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
//@MapperScan(basePackages = {"com.pniutong.qibei.heartbeat.repository.dao"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisAutoConfiguration {

    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // springboot中使用mybatis，设置了type alias好像有问题，推荐写全类名,所以该starter不支持定义alias。
        // factoryBean.setTypeAliasesPackage("com.example.project.domain");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // mapper文件的路径不支持自定义，遵循约定胜于配置，保证每个项目的统一。而且也没有自定义mapper文件目录的需求
        // mapper文件的路径统一为/src/main/resources/mybatis/mappers/***.xml
        factoryBean.setMapperLocations(resolver.getResources("classpath*:mybatis/mappers/**/*.xml"));
        // 设置mybatis的配置文件，配置文件中有分页插件,项目中不再需要进行配置
        factoryBean.setConfigLocation(resolver.getResource("classpath:mybatis_config.xml"));

        return factoryBean.getObject();
    }

    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
