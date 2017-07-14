package com.leibangzhu.starters.mq.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;

public class RocketMqProducerBuilder {
    private String nameServer = "";
    private String groupName = "ProducerGroupName";
    private String instanceName = "Producer";

    public static RocketMqProducerBuilder builder(){
        return new RocketMqProducerBuilder();
    }

    public RocketMqProducerBuilder nameServer(String nameServer){
        this.nameServer = nameServer;
        return this;
    }

    public RocketMqProducerBuilder groupName(String groupName){
        this.groupName = groupName;
        return this;
    }

    public RocketMqProducerBuilder instanceName(String instanceName){
        this.instanceName = instanceName;
        return this;
    }

    public RocketMqProducer build() throws MQClientException {
        return new RocketMqProducer(this);
    }

    public String getNameServer() {
        return nameServer;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getInstanceName() {
        return instanceName;
    }
}
