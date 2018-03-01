package com.leibangzhu.starters.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.leibangzhu.starters.message.api.IMsgConsumer;

import java.util.concurrent.TimeUnit;

public final class CacheBuilder<K,V> {
    // maximumSize
    private int maximumSize = -1;
    // expireAfterWrite
    private int expireAfterWriteDuration = -1;
    private TimeUnit expireAfterWriteTimeUnit;
    // expireAfterAccess
    private int expireAfterAccessDuration = -1;
    private TimeUnit expireAfterAccessTimeUnit;
    // 用于数据同步
    private IMsgConsumer consumer;

    public CacheBuilder<K,V> maximumSize(int maximumSize){
        this.maximumSize = maximumSize;
        return this;
    }

    public CacheBuilder<K,V> expireAfterWrite(int duration,TimeUnit timeUnit){
        this.expireAfterWriteDuration = duration;
        this.expireAfterWriteTimeUnit = timeUnit;
        return this;
    }

    public CacheBuilder<K,V> expireAfterAccess(int duration,TimeUnit timeUnit){
        this.expireAfterAccessDuration = duration;
        this.expireAfterAccessTimeUnit = timeUnit;
        return this;
    }

    public CacheBuilder<K,V> consumer(IMsgConsumer consumer){
        this.consumer = consumer;
        return this;
    }

    public <K,V> ICache<K,V> build(){
        return new Cache(this);
    }



    public IMsgConsumer getConsumer() {
        return consumer;
    }

    public com.github.benmanes.caffeine.cache.Cache<K, V> getCache() {
        Caffeine caffeine = Caffeine.newBuilder();
        // maximumSize
        if (maximumSize > 0){
            caffeine = caffeine.maximumSize(maximumSize);
        }
        // expireAfterWrite
        if(expireAfterWriteDuration > 0){
            caffeine = caffeine.expireAfterWrite(this.expireAfterWriteDuration,this.expireAfterWriteTimeUnit);
        }
        // expireAfterAccess
        if(expireAfterAccessDuration > 0){
            caffeine = caffeine.expireAfterWrite(this.expireAfterAccessDuration,this.expireAfterAccessTimeUnit);
        }
        return caffeine.build();
    }
}
