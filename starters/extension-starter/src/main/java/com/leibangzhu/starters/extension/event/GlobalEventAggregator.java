package com.leibangzhu.starters.extension.event;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.leibangzhu.starters.common.eventaggregator.ForceRetryException;
import com.leibangzhu.starters.common.eventaggregator.IEvent;
import com.leibangzhu.starters.common.eventaggregator.LocalEventAggregator;
import com.leibangzhu.starters.common.util.QibeiLogger;
import com.leibangzhu.starters.extension.event.data.EventField;
import com.leibangzhu.starters.extension.event.data.EventMessage;
import com.leibangzhu.starters.extension.event.exception.EventAggregatorException;
import com.leibangzhu.starters.extension.event.mock.MockMessageConsumer;
import com.leibangzhu.starters.extension.event.mock.MockMessageProducer;
import com.leibangzhu.starters.mq.IMsgHandler;
import com.leibangzhu.starters.mq.Message;
import com.leibangzhu.starters.mq.MessageBuilder;
import com.leibangzhu.starters.mq.exception.MQException;
import com.leibangzhu.starters.mq.ons.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by lili on 2016/10/8.
 */
@Component
public class GlobalEventAggregator extends LocalEventAggregator implements IGlobalEventAggregator, IMsgHandler, InitializingBean {

    private static final Logger logger = QibeiLogger.create(GlobalEventAggregator.class);

    private static final String EVENT_TAG_FORMAT = "Event_%s";
    public static final String EVENT_TAG_SEPARATOR = "||";
    private static final String EVENT_ID_FORMAT = "EventID_%s_%s_%s";

    @Autowired
    private IEventAggregatorConfiguration config;

    private IOnsProducer producer;

    private IOnsConsumer consumer;

    private boolean started = false;

    public GlobalEventAggregator() {

        super(2);
    }

    private IOnsProducer createProducer() throws EventAggregatorException {

        if (config.isMocked()) {

            return new MockMessageProducer();
        }

        validateServerConnectionConfiguration(config);

        if (StringUtils.isEmpty(config.getTopic())) {

            throw new EventAggregatorException(String.format("EventMQ's topic for Producer '%s' is undefined.", config.getProducerId()));
        }

        OnsProducerBuilder builder = OnsProducerBuilder.builder().accessKey(config.getAccessKey()).secretKey(config.getAccessSecret()).topic(config.getTopic()).producerId(config.getProducerId());

        try {

            return new OnsProducer(builder);
        } catch (MQClientException e) {

            throw new EventAggregatorException("Cannot create Ons consumer for Global Event Aggregator.");
        }
    }

    private IOnsConsumer createConsumer() throws EventAggregatorException {

        if (config.isMocked()) {

            return new MockMessageConsumer();
        }

        validateServerConnectionConfiguration(config);

        OnsConsumerBuilder builder = OnsConsumerBuilder.builder().accessKey(config.getAccessKey()).secretKey(config.getAccessSecret()).consumerId(config.getConsumerId());

        try {
            return new OnsConsumer(builder);
        } catch (com.alibaba.rocketmq.client.exception.MQClientException e) {

            throw new EventAggregatorException("Cannot create Ons consumer for Global Event Aggregator.");
        }
    }

    private void validateServerConnectionConfiguration(IEventAggregatorConfiguration config) throws EventAggregatorException {

        if (StringUtils.isEmpty(config.getAccessKey()) || StringUtils.isEmpty(config.getAccessSecret())) {

            throw new EventAggregatorException("(AccessKey/SecretKey) are undefined, both of them must be defined.");
        }
    }

    @Override
    public void start() throws EventAggregatorException, MQException {

        if (false == started) {

            if (StringUtils.isNoneEmpty(config.getProducerId())) {

                producer = createProducer();
                producer.start();
            }

            if (StringUtils.isNoneEmpty(config.getConsumerId())) {

                consumer = createConsumer();

                registerGlobalSubscriber();

                consumer.start();
            }

            started = true;
        }
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public void publish(IEvent event) throws Exception {

        if (null == producer) {
            throw new EventAggregatorException("Produce is not initialized.");
        }

        event.getParams().put(EventField.EVENT_ID, generateEventId(event));

        EventMessage message = EventMessage.FromEvent(event);

        MessageBuilder builder = MessageBuilder.builder().key(message.getId()).tag(String.format(EVENT_TAG_FORMAT, event.getName())).body(message.toMessage());

        producer.send(new Message(builder));
    }

    @Override
    public MsgHandleStatus process(Message msg) {

        String messageString = msg.getBody();
        EventMessage eventMessage = EventMessage.FromEventMessage(messageString);

        try {

            processHandlers(eventMessage.getEvent());
        }catch (ForceRetryException e){

            logger.info(String.format("The force retry is triggered by '%s' on reason '%s',", e.getHandlerName(), e.getTriggerMessage()));
            return MsgHandleStatus.FAIL;
        }

        return MsgHandleStatus.SUCCESS;
    }

    private void registerGlobalSubscriber() throws MQException {

        consumer.subscribe("*", this);
    }

    private String generateEventId(IEvent event) throws UnknownHostException {

        return String.format(EVENT_ID_FORMAT, event.getName(), InetAddress.getLocalHost(), System.nanoTime());
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        start();
    }
}
