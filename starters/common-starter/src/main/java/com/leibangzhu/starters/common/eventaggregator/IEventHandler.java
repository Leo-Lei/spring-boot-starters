package com.leibangzhu.starters.common.eventaggregator;

import java.util.Map;

public interface IEventHandler {

    boolean accept(IEvent event);
    void handle(Map<String,Object> params) throws ForceRetryException;
}
