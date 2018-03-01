package com.leibangzhu.starters.influxdb;


import com.leibangzhu.starters.common.util.LeibangzhuLogger;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "influxdb")
public class InfluxDBAutoConfiguration {

    private static final Logger logger = LeibangzhuLogger.create(InfluxDBAutoConfiguration.class);

    private String url;
    private String username;
    private String password;

    @Bean
    public InfluxDB influxDB(){

        InfluxDB influxDB = null;
        if(StringUtils.isNoneEmpty(url)){

            influxDB = InfluxDBFactory.connect(url, username, password);
        }else{

            logger.warn("influxdb.url is missing in application.properties if you want to use InfluxDB.");
        }
        return influxDB;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {

        return StringUtils.isNoneEmpty(username) ? username : "root";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {

        return StringUtils.isNoneEmpty(password) ? password : "root";
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
