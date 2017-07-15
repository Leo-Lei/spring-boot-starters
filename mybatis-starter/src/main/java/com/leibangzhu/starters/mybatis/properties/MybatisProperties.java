package com.leibangzhu.starters.mybatis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.mybatis")
public class MybatisProperties {

    private String mapperScanBasePackage = "";


    public String getMapperScanBasePackage() {
        return mapperScanBasePackage;
    }

    public void setMapperScanBasePackage(String mapperScanBasePackage) {
        this.mapperScanBasePackage = mapperScanBasePackage;
    }
}
