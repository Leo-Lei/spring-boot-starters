package com.leibangzhu.starters.message.etcd;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.Watch;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.options.WatchOption;
import com.coreos.jetcd.watch.WatchEvent;
import com.leibangzhu.starters.message.api.FilterOption;
import com.leibangzhu.starters.message.api.IMsgConsumer;
import com.leibangzhu.starters.message.api.IMsgHandler;
import com.leibangzhu.starters.message.api.Message;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

public class EtcdMsgConsumer implements IMsgConsumer {

    private String endpoints;
    private Client client;
    private IMsgHandler handler;
    private FilterOption filterOption;

    private EtcdMsgConsumer(EtcdMsgConsumerBuilder builder){
        this.endpoints = builder.endpoints;
        this.filterOption = builder.getFilterOption();
        client = Client.builder().endpoints(endpoints).build();
        this.handler = builder.handler;
    }

    @Override
    public void start() {
        //Watch watch = client.getWatchClient();
        String topic = filterOption.getTopic();
        Set<String> tags = filterOption.getTags();

        boolean watchAllTags = tags.contains("*");
        if (watchAllTags){
            watch(topic,"",true);
        }else {
             Iterator<String> iterable = tags.iterator();
             while (iterable.hasNext()){
                 watch(topic,iterable.next(),false);
             }
        }
    }

    private void watch(String topic,String tag,boolean watchAll) {
        Watch watch = client.getWatchClient();
        String key;
        if (watchAll){
            key = MessageFormat.format("/messages/{0}",topic);
        }else {
            key = MessageFormat.format("/messages/{0}/{1}",topic,tag);
        }
        Watch.Watcher watcher = watch.watch(
                ByteSequence.fromString(key ),
                WatchOption.newBuilder().withNoDelete(true).withPrefix(ByteSequence.fromString(key)).build()
        );

        Executors.newSingleThreadExecutor().submit((Runnable) () -> {
            while (true) {
                try {
                    for (WatchEvent event : watcher.listen().getEvents()) {
                        //   key:  /messages/topic/tag/messageId
                        String eventKey = event.getKeyValue().getKey().toStringUtf8();
                        String eventTopic = eventKey.split("/")[2];
                        String eventTag = eventKey.split("/")[3];

                        if (null != handler){
                            Message message = Message
                                    .builder()
                                    .body(event.getKeyValue().getValue().toStringUtf8())
                                    .header("topic",eventTopic)
                                    .header("tag",eventTag)
                                    .build();

                            handler.handle(message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void registerHandler(IMsgHandler handler) {
        this.handler = handler;
    }

    @Override
    public void subscribe(FilterOption filterOption) {

    }

    public static EtcdMsgConsumerBuilder builder(){
        return new EtcdMsgConsumerBuilder();
    }

    public static class EtcdMsgConsumerBuilder {

        private String endpoints;
        private IMsgHandler handler;
        private String topic;
        private List<String> tags;

        public EtcdMsgConsumerBuilder endpoints(String endpoints){
            this.endpoints = endpoints;
            return this;
        }

        public EtcdMsgConsumer build(){
            return new EtcdMsgConsumer(this);
        }

        public EtcdMsgConsumerBuilder registerHandler(IMsgHandler handler){
            this.handler = handler;
            return this;
        }

        public EtcdMsgConsumerBuilder topic(String topic){
            this.topic = topic;
            return this;
        }

        public EtcdMsgConsumerBuilder tags(String... tags){
            this.tags = Arrays.asList(tags) ;
            return this;
        }

        public FilterOption getFilterOption(){
            // List<String>  =>  tags...
            String[] list =  new String[tags.size()];
            tags.toArray(list);
            return FilterOption.builder().topic(topic).tags(list).build();
        }
    }
}
