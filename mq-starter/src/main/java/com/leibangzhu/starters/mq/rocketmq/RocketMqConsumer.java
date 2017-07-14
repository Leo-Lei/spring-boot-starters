package com.leibangzhu.starters.mq.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.leibangzhu.starters.mq.IMsgConsumer;
import com.leibangzhu.starters.mq.IMsgHandler;
import com.leibangzhu.starters.mq.Message;
import com.leibangzhu.starters.mq.MessageBuilder;

import java.awt.image.ImageConsumer;
import java.util.List;
import java.util.Map;

public class RocketMqConsumer implements IMsgConsumer{
    public RocketMqConsumer(RocketMqConsumerBuilder builder) throws MQClientException {
        DefaultMQPushConsumer consumer; consumer = new DefaultMQPushConsumer();
        consumer.setConsumerGroup(builder.getGroupName());
        consumer.setNamesrvAddr(builder.getNameServer());
        consumer.setInstanceName(builder.getInstanceName());
        for(Map.Entry<String,String> entry : builder.getTopicsAndTags().entrySet()){
            consumer.subscribe(entry.getKey(),entry.getValue());
        }

//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//
//                System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs.size());
//
//                MessageExt msg = msgs.get(0);
//                Message message = MessageBuilder.builder()
//                        .topic(msg.getTopic())
//                        .tag(msg.getTags())
//                        .key(msg.getKeys())
//                        .body( new String(msg.getBody()))
//                        .build();
//                IMsgHandler.MsgHandleStatus status = builder.getHandler().process(message);
//
//                return status == IMsgHandler.MsgHandleStatus.SUCCESS ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS : ConsumeConcurrentlyStatus.RECONSUME_LATER;
//            }
//        });

        consumer.start();
    }
}
