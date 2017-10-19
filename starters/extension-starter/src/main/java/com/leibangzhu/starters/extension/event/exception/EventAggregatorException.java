package com.leibangzhu.starters.extension.event.exception;

/**
 * Created by lili on 2016/10/10.
 */
public class EventAggregatorException extends Exception {

    public EventAggregatorException(String message, Throwable cause){

        super(message, cause);
    }

    public EventAggregatorException(String message){

        super(message);
    }
}
