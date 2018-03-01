package com.leibangzhu.starters.message.etcd;

import com.leibangzhu.starters.message.api.IMsgProducer;
import com.leibangzhu.starters.message.etcd.config.EtcdProducerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "message.etcd.producer", value = "endpoints")
@EnableConfigurationProperties({EtcdProducerProperties.class})
public class EtcdProducerConfiguration {

    @Autowired
    private EtcdProducerProperties properties;

    @Bean
    public IMsgProducer producer(){
        IMsgProducer producer = EtcdMsgProducer.newBuilder()
                .endpoints(properties.getEndpoints())
                .build();

        return producer;
    }
}
