package com.leibangzhu.starters.message.etcd;

import com.leibangzhu.starters.message.api.IMsgConsumer;
import com.leibangzhu.starters.message.etcd.config.EtcdConsumerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "message.etcd.consumer", value = "endpoints")
@EnableConfigurationProperties({EtcdConsumerProperties.class})
public class EtcdConsumerConfiguration {

    @Autowired
    private EtcdConsumerProperties properties;

    @Bean
    public IMsgConsumer consumer(){
        IMsgConsumer consumer = EtcdMsgConsumer.builder()
                .endpoints(properties.getEndpoints())
                .topic(properties.getTopic())
                .tags(properties.getTags().split(","))
//                .registerHandler(new MockMsgHandler())
                .build();
        return consumer;
    }
}
