package com.leibangzhu.starters.message.etcd;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.options.PutOption;
import com.leibangzhu.starters.message.api.IMsgProducer;
import com.leibangzhu.starters.message.api.Message;

import java.text.MessageFormat;
import java.util.UUID;

public class EtcdMsgProducer implements IMsgProducer {

    private static final String DEFAULT_TAGS = "default";
    private Client client;

    public EtcdMsgProducer(EtcdMsgProducerBuilder builder){
        String endpoints = builder.getEndpoints();
        this.client = Client.builder().endpoints(endpoints).build();
    }

    public static EtcdMsgProducerBuilder newBuilder(){
        return new EtcdMsgProducerBuilder();
    }

    @Override
    public void send(Message message) {
        String topic = message.getTopic();
        String tag = null == message.getTag() ? DEFAULT_TAGS : message.getTag() ;
        String messageId = UUID.randomUUID().toString();
        String key = MessageFormat.format("/messages/{0}/{1}/{2}",topic,tag,messageId);
        // 发送的消息保留5秒,messageId使用UUID,假设这5秒内，UUID不重复
        // todo: 可以考虑使用本机IP加上UUID作为messageId，可以保证messageId不唯一
        try {
            client.getKVClient().put(
                    ByteSequence.fromString(key),
                    ByteSequence.fromString(message.getBody()),
                    PutOption.newBuilder().withLeaseId(client.getLeaseClient().grant(60).get().getID()).build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
