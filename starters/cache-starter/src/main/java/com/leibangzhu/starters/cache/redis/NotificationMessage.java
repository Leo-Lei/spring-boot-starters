package com.leibangzhu.starters.cache.redis;

import org.apache.commons.lang3.StringUtils;

public class NotificationMessage {

    private static final String SEPERATOR = "@--@";

    private String id;
    private String message;

    public NotificationMessage(String id, String message) {

        this.setId(id);
        this.setMessage(message);
    }

    public String toMessage(){

        return String.format("%s%s%s", getId(), SEPERATOR, getMessage());
    }

    public static NotificationMessage fromMessage(String message) {

        String[] tokens = StringUtils.split(message, SEPERATOR);

        if(2 == tokens.length){

            return new NotificationMessage(tokens[0], tokens[1]);
        }

        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
