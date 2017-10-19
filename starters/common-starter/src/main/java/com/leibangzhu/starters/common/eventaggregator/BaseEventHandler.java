package com.leibangzhu.starters.common.eventaggregator;


import java.util.Map;

/**
 * Author: lili
 * Date: 2016/12/30
 * Time: 11:53
 */
public abstract class BaseEventHandler implements IEventHandler {

    @Override
    public boolean accept(IEvent event) {

        return canHandle(event);
    }

    public abstract void handle(Map<String, Object> params) throws ForceRetryException;

    protected boolean canHandle(IEvent event){

        return true;
    };
}
