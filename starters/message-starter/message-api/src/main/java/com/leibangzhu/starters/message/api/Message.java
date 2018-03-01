package com.leibangzhu.starters.message.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Message {
    private String topic;
    private String tag;
    private Map<String,String> headers = new ConcurrentHashMap<>();
    private String body;

    public Message(MessageBuilder builder){
        this.topic = builder.topic;
        this.tag = builder.tag;
        this.body = builder.body;
        this.headers = builder.headers;
    }

    public String getBody() {
        return body;
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public boolean containsHeader(String header){
        return headers.containsKey(header);
    }

    public static MessageBuilder builder(){
        return new MessageBuilder();
    }

    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }

    public static class MessageBuilder{
        private String topic;
        private String tag;
        private String body;
        private Map<String,String> headers = new ConcurrentHashMap<>();

        public MessageBuilder topic(String topic){
            this.topic = topic;
            return this;
        }

        public MessageBuilder tag(String tag){
            this.tag = tag;
            return this;
        }

        public MessageBuilder body(String body){
            this.body = body;
            return this;
        }

        public MessageBuilder header(String key,String value){
            headers.put(key,value);
            return this;
        }

        public Message build(){
            return new Message(this);
        }
    }
}
