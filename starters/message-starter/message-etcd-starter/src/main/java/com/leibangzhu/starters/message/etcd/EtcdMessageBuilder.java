package com.leibangzhu.starters.message.etcd;

public class EtcdMessageBuilder {
    private String body;
    private String topic;
    private String tag;

    public EtcdMessageBuilder body(String body){
        this.body = body;
        return this;
    }

    public EtcdMessageBuilder topic(String topic){
        this.topic = topic;
        return this;
    }

    public EtcdMessageBuilder tag(String tag){
        this.tag = tag;
        return this;
    }

    public EtcdMessage build(){
        return new EtcdMessage(this);
    }

    public String getBody() {
        return body;
    }

    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }
}
