package com.leibangzhu.starters.mq.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.leibangzhu.starters.mq.IMsgHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class RocketMqConsumerBuilder {
    private String groupName = "";        // default value
    private String nameServer = "";       // default value
    private String instanceName = "";     // default value

    private static final String DEFAULT_TAGS = "*";

    private Map<String,String> topicsAndTags = new LinkedHashMap();
    private IMsgHandler handler;

    public static RocketMqConsumerBuilder builder() {
        return new RocketMqConsumerBuilder();
    }

    public RocketMqConsumerBuilder groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public RocketMqConsumerBuilder nameServer(String nameServer) {
        this.nameServer = nameServer;
        return this;
    }

    public RocketMqConsumerBuilder instanceName(String instanceName) {
        this.instanceName = instanceName;
        return this;
    }

    public RocketMqConsumer build() throws MQClientException {
        return new RocketMqConsumer(this);
    }

    public RocketMqConsumerBuilder subscribe(String topic,String tags){
        topicsAndTags.put(topic,tags);
        return this;
    }

    public RocketMqConsumerBuilder subscribe(String topic){
        topicsAndTags.put(topic,DEFAULT_TAGS);
        return this;
    }

    public RocketMqConsumerBuilder registerMsgHandler(IMsgHandler handler){
        this.handler = handler;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getNameServer() {
        return nameServer;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public Map<String,String> getTopicsAndTags(){
        return topicsAndTags;
    }


    public IMsgHandler getHandler() {
        return handler;
    }
}
