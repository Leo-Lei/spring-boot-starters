package com.leibangzhu.starters.sms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by reinhard on 04/08/2017.
 */

@Configuration
public class SmsAutoConfiguration {

    @Bean
    public ICompositeSmsClient smsClient(){

        return new CompositeSmsClient();
    }
}
