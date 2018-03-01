package com.leibangzhu.starters.message.etcd;

public class EtcdMessage  {
    private String body;
    private String topic;
    private String tag;

    public EtcdMessage(EtcdMessageBuilder builder){
        this.body = builder.getBody();
        this.topic = builder.getTopic();
        this.tag = builder.getTag();
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
