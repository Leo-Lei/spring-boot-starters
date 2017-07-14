package com.leibangzhu.starters.mq.ons;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;

public class OnsProducerBuilder {
    //    private String groupName = "";        // default value
//    private String instanceName = "";     // default value
    private String accessKey = "";
    private String secretKey = "";
    private String topic = "";
    private String producerId = "";

    public static OnsProducerBuilder builder(){
        return new OnsProducerBuilder();
    }

    public OnsProducerBuilder accessKey(String accessKey){
        this.accessKey = accessKey;
        return this;
    }

    public OnsProducerBuilder secretKey(String secretKey){
        this.secretKey = secretKey;
        return this;
    }

    public OnsProducerBuilder topic(String topic){
        this.topic = topic;
        return this;
    }

    public OnsProducerBuilder producerId(String producerId){
        this.producerId = producerId;
        return this;
    }

    public OnsProducer build() throws MQClientException {
        return new OnsProducer(this);
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getTopic() {
        return topic;
    }

    public String getProducerId() {
        return producerId;
    }
}
