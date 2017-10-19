package com.leibangzhu.starters.mq;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

public class Message {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private String id;
    private String topic;
    private String tag;
    private String key;
    private String body;

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

    public Message(MessageBuilder builder){
        this.id = builder.getId();
        this.topic = builder.getTopic();
        this.tag = builder.getTag();
        this.key = builder.getKey();
        this.body = builder.getBody();
    }

    @Override
    public String toString(){
        return MessageFormat.format("{topic:{0}, tag:{1}, key:{2}, body:{3} }", topic,tag,key,body);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getBodyBytes() {

        if (null != body) {

            try {

                return body.getBytes(DEFAULT_ENCODING);
            } catch (UnsupportedEncodingException e) {

                //Do nothing.
                return null;
            }
        }else{

            return null;
        }
    }
}
