package com.leibangzhu.starters.mq.ons;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.leibangzhu.starters.common.util.QibeiLogger;
import com.leibangzhu.starters.mq.IMsgHandler;
import com.leibangzhu.starters.mq.Message;
import com.leibangzhu.starters.mq.MessageBuilder;
import com.leibangzhu.starters.mq.exception.MQException;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.Properties;

public class OnsConsumer implements IOnsConsumer, InitializingBean, DisposableBean {

    private static final Logger logger = QibeiLogger.create(OnsConsumer.class);

    private Consumer consumer;

    public OnsConsumer(OnsConsumerBuilder builder) throws MQClientException {

        Properties properties = new Properties();
        properties.put(OnsPropertiesKeys.ACCESS_KEY, builder.getAccessKey());
        properties.put(OnsPropertiesKeys.SECRET_KEY, builder.getSecretKey());
        properties.put(OnsPropertiesKeys.CONSUMER_ID, builder.getConsumerId());

        consumer = ONSFactory.createConsumer(properties);

        if (null != builder.getHandler()) {

            for (Map.Entry<String, String> entry : builder.getTopicsAndTags().entrySet()) {

                subscribe(entry.getKey(), entry.getValue(), builder.getHandler());
            }

            consumer.start();
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
    @Override
    public void subscribe(String topic, String filterExpression, IMsgHandler handler) {

        consumer.subscribe(topic, filterExpression, (message, context) -> {
            Message msg = MessageBuilder.builder()
                    .topic(message.getTopic())
                    .tag(message.getTag())
                    .key(message.getKey())
                    .body(new String(message.getBody()))
                    .id(message.getMsgID())
                    .build();

            if (logger.isDebugEnabled()) {

                logger.debug(String.format("Processing message (%s) from topic (%s) is starting.", message.getMsgID(), message.getTopic()));
            }

            IMsgHandler.MsgHandleStatus result = handler.process(msg);

            if (logger.isDebugEnabled()) {

                logger.debug(String.format("Processed message (%s) with result (%s) from topic (%s) is finished.", message.getMsgID(), result, message.getTopic()));
            }

            return result == IMsgHandler.MsgHandleStatus.SUCCESS ? Action.CommitMessage : Action.ReconsumeLater;
        });
    }

    @Override
    public void subscribe(String topic, IMsgHandler handler) throws MQException {

        subscribe(topic, "*", handler);
    }

    @Override
    public void unsubscribe(String topic) {

        consumer.unsubscribe(topic);

        if (logger.isInfoEnabled()) {

            logger.info(String.format("Top (%s) is unsubscribed.", topic));
        }
    }

    public void start() {

        if (null != consumer && !consumer.isStarted()) {

            consumer.start();
            if (logger.isInfoEnabled()) {
                logger.info(String.format("MQ consumer '%s' is started.", this.hashCode()));
            }
        }
    }

    public void shutdown() {

        if (!consumer.isClosed()) {

            consumer.shutdown();
            if (logger.isInfoEnabled()) {

                logger.info(String.format("MQ consumer '%s' is shutdown.", this.hashCode()));
            }
        }
    }
}
