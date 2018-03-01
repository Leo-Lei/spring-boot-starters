package com.leibangzhu.starters.cache.redis;

import com.leibangzhu.starters.common.date.DateTime;

public class ValueWrapper<T> {

    private T value;
    private DateTime lastUpdatedTime;

    public ValueWrapper(T value) {

        refresh(value);
    }

    public T getValue() {
        return value;
    }

    public DateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void refresh(T value){

        this.value = value;
        this.lastUpdatedTime = DateTime.now();
    }
}
