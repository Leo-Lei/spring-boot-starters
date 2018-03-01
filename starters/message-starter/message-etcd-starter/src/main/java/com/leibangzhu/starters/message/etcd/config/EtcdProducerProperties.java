package com.leibangzhu.starters.message.etcd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "message.etcd.producer")
public class EtcdProducerProperties {

    private String endpoints = "http://127.0.0.1:2379";

    public String getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(String endpoints) {
        this.endpoints = endpoints;
    }
}
