package com.leibangzhu.starters.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by reinhard on 04/08/2017.
 */

@Configuration
public class HttpBrokerAutoConfiguration {

    @Bean
    public IHttpBroker httpBroker(){

        return new HttpBroker();
    }
}
