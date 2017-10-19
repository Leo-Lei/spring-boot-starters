package com.leibangzhu.starters.common;


import com.leibangzhu.starters.common.http.HttpClient;
import com.leibangzhu.starters.common.http.IHttpClient;
import com.leibangzhu.starters.common.http.properties.HttpClientProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class HttpClientAutoConfiguration {

    @Primary
    @Bean(value = "defaultHttpClientProperties")
    @ConditionalOnMissingBean(name = {"defaultHttpClientProperties"})
    @ConfigurationProperties(prefix = "http.client")
    public HttpClientProperties httpClientProperties(){
        return new HttpClientProperties();
    }

    @ConditionalOnMissingBean({HttpClient.class})
    @ConditionalOnBean(name = {"defaultHttpClientProperties"})
    @Bean(value = "defaultHttpClient")
    public IHttpClient httpClient(@Qualifier("defaultHttpClientProperties") HttpClientProperties properties){
        return new HttpClient(properties);
    }

}
