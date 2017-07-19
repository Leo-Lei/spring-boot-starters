package com.leibangzhu.starters.mq.ons;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.leibangzhu.starters.mq.IMsgHandler;
import com.leibangzhu.starters.mq.rocketmq.RocketMqConsumer;
import com.leibangzhu.starters.mq.rocketmq.RocketMqConsumerBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class OnsConsumerBuilder {
    private String accessKey;
    private String secretKey;
    private String consumerId;


//    private String groupName = "";        // default value
//    private String nameServer = "";       // default value
//    private String instanceName = "";     // default value

    private static final String DEFAULT_TAGS = "*";

    private Map<String,String> topicsAndTags = new LinkedHashMap();
    private IMsgHandler handler;

    public static OnsConsumerBuilder builder() {
        return new OnsConsumerBuilder();
    }

    public OnsConsumerBuilder accessKey(String accessKey){
        this.accessKey = accessKey;
        return this;
    }

    public OnsConsumerBuilder secretKey(String secretKey){
        this.secretKey = secretKey;
        return this;
    }

    public OnsConsumerBuilder consumerId(String consumerId){
        this.consumerId = consumerId;
        return this;
    }

//    public OnsConsumerBuilder groupName(String groupName) {
//        this.groupName = groupName;
//        return this;
//    }
//
//    public OnsConsumerBuilder nameServer(String nameServer) {
//        this.nameServer = nameServer;
//        return this;
//    }
//
//    public OnsConsumerBuilder instanceName(String instanceName) {
//        this.instanceName = instanceName;
//        return this;
//    }

    public OnsConsumer build() throws MQClientException {
        return new OnsConsumer(this);
    }

    public OnsConsumerBuilder subscribe(String topic,String tags){
        topicsAndTags.put(topic,tags);
        return this;
    }

    public OnsConsumerBuilder subscribe(String topic){
        topicsAndTags.put(topic,DEFAULT_TAGS);
        return this;
    }

    public OnsConsumerBuilder registerMsgHandler(IMsgHandler handler){
        this.handler = handler;
        return this;
    }

//    public String getGroupName() {
//        return groupName;
//    }
//
//    public String getNameServer() {
//        return nameServer;
//    }
//
//    public String getInstanceName() {
//        return instanceName;
//    }

    public Map<String,String> getTopicsAndTags(){
        return topicsAndTags;
    }


    public IMsgHandler getHandler() {
        return handler;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getConsumerId() {
        return consumerId;
    }
}
