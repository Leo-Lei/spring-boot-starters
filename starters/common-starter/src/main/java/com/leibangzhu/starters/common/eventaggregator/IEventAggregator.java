package com.leibangzhu.starters.common.eventaggregator;

public interface IEventAggregator {
    void publish(IEvent event) throws Exception;
    void subscribe(IEvent event, IEventHandler handler);
}
