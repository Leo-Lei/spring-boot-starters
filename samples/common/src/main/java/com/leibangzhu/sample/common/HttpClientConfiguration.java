package com.leibangzhu.sample.common;

import com.leibangzhutech.starters.common.http.HttpClient;
import com.leibangzhutech.starters.common.http.IHttpClient;
import com.leibangzhutech.starters.common.http.properties.HttpClientProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfiguration {

    @Bean(value = "httpClientAProperties")
    @ConfigurationProperties(prefix = "http.client.a")
    public HttpClientProperties httpClientProperties(){
        return new HttpClientProperties();
    }

    @Bean(value = "httpClientA")
    public IHttpClient httpClientA(@Qualifier("httpClientAProperties") HttpClientProperties properties){
        return new HttpClient(properties);
    }

    @Bean(value = "httpClientB")
    public IHttpClient httpClientB(HttpClientProperties properties){
        return new HttpClient(properties);
    }

}
