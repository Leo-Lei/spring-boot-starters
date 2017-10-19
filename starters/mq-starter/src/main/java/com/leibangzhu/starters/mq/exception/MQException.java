package com.leibangzhu.starters.mq.exception;

/**
 * Author: lili
 * Date: 2016/12/16
 * Time: 14:38
 */
public class MQException extends Exception {

    public MQException(String errorMessage) {

        super(errorMessage);
    }

    public MQException(String errorMessage, Throwable t){

        super(errorMessage, t);
    }
}
