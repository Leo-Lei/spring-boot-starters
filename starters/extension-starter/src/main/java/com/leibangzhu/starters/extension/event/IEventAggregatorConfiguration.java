package com.leibangzhu.starters.extension.event;

/**
 * Created by lili on 2016/10/9.
 */
public interface IEventAggregatorConfiguration {

    String getAccessSecret();
    String getAccessKey();
    String getTopic();
    String getProducerId();
    String getConsumerId();
    boolean isMocked();
}
