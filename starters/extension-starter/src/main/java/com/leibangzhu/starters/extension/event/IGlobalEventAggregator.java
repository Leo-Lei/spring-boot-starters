package com.leibangzhu.starters.extension.event;

import com.leibangzhu.starters.common.eventaggregator.IEventAggregator;

/**
 * Created by lili on 2016/10/8.
 */
public interface IGlobalEventAggregator extends IEventAggregator {

    void start() throws Exception;
    boolean isStarted();
}
