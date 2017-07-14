package com.leibangzhu.starters.mq.ons;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.leibangzhu.starters.mq.IMsgHandler;
import com.leibangzhu.starters.mq.Message;
import com.leibangzhu.starters.mq.MessageBuilder;

import java.util.Map;
import java.util.Properties;

public class OnsConsumer {
    public OnsConsumer(OnsConsumerBuilder builder) throws MQClientException {

        Properties properties = new Properties();
        properties.put(OnsPropertiesKeys.ACCESS_KEY,builder.getAccessKey());
        properties.put(OnsPropertiesKeys.SECRET_KEY,builder.getSecretKey());
        properties.put(OnsPropertiesKeys.CONSUMER_ID,builder.getConsumerId());

        com.aliyun.openservices.ons.api.Consumer consumer = ONSFactory.createConsumer(properties);

        for(Map.Entry<String,String> entry : builder.getTopicsAndTags().entrySet()){
            consumer.subscribe(entry.getKey(),entry.getValue(),(message, context) -> {
                Message msg = MessageBuilder.builder()
                        .topic(message.getTopic())
                        .tag(message.getTag())
                        .key(message.getKey())
                        .body( new String(message.getBody()))
                        .id(message.getMsgID())
                        .build();

                IMsgHandler.MsgHandleStatus result = builder.getHandler().process(msg);
                return result == IMsgHandler.MsgHandleStatus.SUCCESS ? Action.CommitMessage : Action.ReconsumeLater;
            });
        }

        consumer.start();
    }
}
