package com.leibangzhu.starters.message.etcd;

public class EtcdMsgProducerBuilder {

    private String endpoints;

    public EtcdMsgProducerBuilder endpoints(String endpoints){
        this.endpoints = endpoints;
        return this;
    }

    public EtcdMsgProducer build(){
        return new EtcdMsgProducer(this);
    }

    public String getEndpoints() {
        return endpoints;
    }
}
