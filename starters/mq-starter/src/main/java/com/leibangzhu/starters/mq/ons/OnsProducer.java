package com.leibangzhu.starters.mq.ons;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.leibangzhu.starters.mq.IMsgProducer;
import com.leibangzhu.starters.mq.Message;
import com.leibangzhu.starters.mq.MsgSendResult;

import java.util.Properties;

public class OnsProducer implements IMsgProducer{
    @Override
    public MsgSendResult send(Message msg) {

        com.aliyun.openservices.ons.api.Message message = new com.aliyun.openservices.ons.api.Message(
                msg.getTopic(),
                msg.getTag(),
                msg.getKey(),
                msg.getBody().getBytes()
        );

        com.aliyun.openservices.ons.api.SendResult result = producer.send(message);

        MsgSendResult sendResult = new MsgSendResult();
        sendResult.setMsgId(result.getMessageId());
        return sendResult;
    }

    private com.aliyun.openservices.ons.api.Producer producer;

    public OnsProducer(OnsProducerBuilder builder) throws MQClientException {
        Properties properties = new Properties();
        properties.put(OnsPropertiesKeys.ACCESS_KEY,builder.getAccessKey());
        properties.put(OnsPropertiesKeys.SECRET_KEY,builder.getSecretKey());
        properties.put(OnsPropertiesKeys.TOPIC,builder.getTopic());
        properties.put(OnsPropertiesKeys.PRODUCER_ID,builder.getProducerId());
        this.producer = ONSFactory.createProducer(properties);
        producer.start();
    }
}
