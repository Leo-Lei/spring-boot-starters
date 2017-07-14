package com.leibangzhu.starters.mq.rocketmq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.leibangzhu.starters.mq.IMsgProducer;
import com.leibangzhu.starters.mq.Message;
import com.leibangzhu.starters.mq.MsgSendResult;

public class RocketMqProducer implements IMsgProducer{
    @Override
    public MsgSendResult send(Message msg) {

        com.alibaba.rocketmq.common.message.Message message = new com.alibaba.rocketmq.common.message.Message(
                msg.getTopic(),                             // topic
                msg.getTag(),                               // tag
                msg.getKey(),                               // key
                msg.getBody().getBytes());                  // body

        SendResult result = null;
        try {
            result = producer.send(message);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MsgSendResult sendResult = new MsgSendResult();
        sendResult.setMsgId(result.getMsgId());
        sendResult.setSendStatus(result.getSendStatus().name());

        return sendResult;
    }

    private DefaultMQProducer producer;

    public RocketMqProducer(RocketMqProducerBuilder builder) throws MQClientException {
        producer = new DefaultMQProducer();
        producer.setNamesrvAddr(builder.getNameServer());
        producer.setProducerGroup(builder.getGroupName());
        producer.setInstanceName(builder.getInstanceName());
        producer.start();
    }
}
