package com.leibangzhu.starters.mq.ons;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.leibangzhu.starters.common.util.LeibangzhuLogger;
import com.leibangzhu.starters.mq.Message;
import com.leibangzhu.starters.mq.MsgSendResult;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Properties;

public class OnsProducer implements IOnsProducer, InitializingBean, DisposableBean {

    private static final Logger logger = LeibangzhuLogger.create(OnsProducer.class);

    private Producer producer;
    private String topic;

    public OnsProducer(OnsProducerBuilder builder) throws MQClientException {
        Properties properties = new Properties();
        properties.put(OnsPropertiesKeys.ACCESS_KEY,builder.getAccessKey());
        properties.put(OnsPropertiesKeys.SECRET_KEY,builder.getSecretKey());
        properties.put(OnsPropertiesKeys.TOPIC,builder.getTopic());
        properties.put(OnsPropertiesKeys.PRODUCER_ID,builder.getProducerId());
        this.producer = ONSFactory.createProducer(properties);

        topic = builder.getTopic();
    }

    @Override
    public MsgSendResult send(Message msg) {

        com.aliyun.openservices.ons.api.Message message = new com.aliyun.openservices.ons.api.Message(
                msg.getTopic(),
                msg.getTag(),
                msg.getKey(),
                msg.getBody().getBytes()
        );

        if(logger.isDebugEnabled()) {

            logger.debug(String.format("Sending message (%s) to topic (%s) is starting.", message.toString(), topic));
        }

        SendResult result = producer.send(message);

        if(logger.isDebugEnabled()) {

            logger.debug(String.format("Sending message to topic (%s) is finished, message id is (%s).", topic, result.getMessageId()));
        }

        MsgSendResult sendResult = new MsgSendResult();
        sendResult.setMsgId(result.getMessageId());
        return new MsgSendResult(result.getMessageId(), null);
    }

    @Override
    public void start() {

        if(!producer.isStarted()) {

            producer.start();

            if(logger.isInfoEnabled()) {
                logger.info(String.format("MQ producer for topic (%s) is started.", topic));
            }
        }
    }

    @Override
    public void shutdown() {

        if(!producer.isClosed()) {

            producer.shutdown();

            if(logger.isInfoEnabled()) {

                logger.info(String.format("MQ producer for topic (%s) is shutdown.", topic));
            }
        }
    }

    @Override
    public void sendOneway(Message message) {

        com.aliyun.openservices.ons.api.Message onsMessage =
                new com.aliyun.openservices.ons.api.Message(
                        topic,
                        message.getTag(),
                        message.getKey(),
                        message.getBodyBytes());

        if(logger.isInfoEnabled()) {

            logger.info(String.format("Sending message [one way] (%s) to topic (%s) is starting.", message.toString(), topic));
        }

        producer.sendOneway(onsMessage);

        if(logger.isInfoEnabled()) {

            logger.info(String.format("Sending message [one way] to topic (%s) is finished.", topic));
        }
    }

    @Override
    public void destroy() throws Exception {

        shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        start();
    }
}
