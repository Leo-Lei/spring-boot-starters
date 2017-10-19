package com.leibangzhu.starters.common.eventaggregator;

/**
 * Created by reinhard on 05/07/2017.
 */
public class ForceRetryException extends Exception {

    private String handlerName;
    private String triggerMessage;

    public ForceRetryException(String handlerName, String triggerMessage){

        this.handlerName = handlerName;
        this.triggerMessage = triggerMessage;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public String getTriggerMessage() {
        return triggerMessage;
    }
}
