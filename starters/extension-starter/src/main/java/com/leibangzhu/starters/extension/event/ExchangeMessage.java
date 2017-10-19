package com.leibangzhu.starters.extension.event;

import java.io.UnsupportedEncodingException;

/**
 * Author: lili
 * Date: 2016/12/16
 * Time: 14:45
 */
public class ExchangeMessage {

    private static final String DEFAULT_TEXT_ENCODING = "UTF-8";
    private String tag;
    private String key;
    private byte[] body;
    private String bodyString;
    private String messageId;

    public ExchangeMessage(String tag, String key, String bodyString) {

        this.tag = tag;
        this.key = key;
        this.bodyString = bodyString;

        try {
            this.body = bodyString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            this.body = bodyString.getBytes();
        }
    }

    public ExchangeMessage(String tag, String key, byte[] body) {

        this.tag = tag;
        this.key = key;
        this.body = body;

        try {
            this.bodyString = new String(body, DEFAULT_TEXT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            this.bodyString = new String(body);
        }
    }

    public String getTag() {
        return tag;
    }

    public String getKey() {
        return key;
    }

    public byte[] getBody() {
        return body;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBodyString() {
        return bodyString;
    }

    @Override
    public String toString(){

        return String.format("Tag(%s)_Key(%s)_Body(%s)",tag, key, bodyString);
    }
}
