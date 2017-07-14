package com.leibangzhu.starters.mq;

public class MessageBuilder {
    private String id = "";
    private String topic = "";
    private String tag = "";
    private String key = "";
    private String body = "";

    public static MessageBuilder builder(){
        return new MessageBuilder();
    }

    public MessageBuilder id(String id){
        this.id = id;
        return this;
    }

    public MessageBuilder topic(String topic){
        this.topic = topic;
        return this;
    }

    public MessageBuilder tag(String tag){
        this.tag = tag;
        return this;
    }

    public MessageBuilder key(String key){
        this.key = key;
        return this;
    }

    public MessageBuilder body(String body){
        this.body = body;
        return this;
    }


    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }

    public String getKey() {
        return key;
    }

    public String getBody() {
        return body;
    }

    public Message build(){
        return new Message(this);
    }

    public String getId() {
        return id;
    }
}
