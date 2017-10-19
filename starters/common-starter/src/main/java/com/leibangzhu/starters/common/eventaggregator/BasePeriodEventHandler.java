package com.leibangzhu.starters.common.eventaggregator;

import java.util.Date;

/**
 * Author: lili
 * Date: 2016/12/29
 * Time: 20:13
 */
public abstract class BasePeriodEventHandler extends BaseEventHandler {

    @Override
    public boolean accept(IEvent event) {

        return !isExpired() && canHandle(event);
    }

    private boolean isExpired() {

        return getExpiredTime().getTime() >= new Date().getTime();
    }

    protected abstract Date getExpiredTime();
}
