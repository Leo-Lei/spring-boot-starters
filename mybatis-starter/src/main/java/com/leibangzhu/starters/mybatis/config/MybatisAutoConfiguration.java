package com.leibangzhu.starters.mybatis.config;

import com.leibangzhu.starters.mybatis.properties.MybatisProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({MybatisProperties.class})
public class MybatisAutoConfiguration {

    @Autowired
    private MybatisProperties mybatisProperties;


    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // springboot中使用mybatis，设置了type alias好像有问题，推荐写全类名
        //factoryBean.setTypeAliasesPackage("com.example.project.domain");

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        // mapper文件的路径不支持自定义，采用约定的目录，保证每个项目的统一。似乎没有这个自定义mapper文件目录的场景
        factoryBean.setMapperLocations(resolver.getResources("classpath*:mybatis/mappers/**/*.xml"));
        // 设置mybatis的配置文件，配置文件中有分页插件
        factoryBean.setConfigLocation(resolver.getResource("classpath:mybatis_config.xml"));

        return factoryBean.getObject();
    }

    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
