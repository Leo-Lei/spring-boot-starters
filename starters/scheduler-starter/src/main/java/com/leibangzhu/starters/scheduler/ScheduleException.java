package com.leibangzhu.starters.scheduler;

/**
 * Author: lili
 * Date: 2016/12/16
 * Time: 14:38
 */
public class ScheduleException extends Exception {

    public ScheduleException(String errorMessage) {

        super(errorMessage);
    }

    public ScheduleException(String errorMessage, Throwable t){

        super(errorMessage, t);
    }
}
