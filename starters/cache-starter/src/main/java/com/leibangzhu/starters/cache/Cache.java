package com.leibangzhu.starters.cache;

import com.leibangzhu.starters.message.api.IMsgConsumer;
import com.leibangzhu.starters.message.api.IMsgHandler;
import com.leibangzhu.starters.message.api.Message;

// 使用已有的本地内存缓存框架,有ehCache,Guava Cache和caffeine cache。推荐caffeine cache，是对guava cache的重写和优化。
// Spring Cache从4.3开始已从Guava Cache转向caffeine cache。
// 不再自己去实现本地缓存的存储部分。多机器间的同步依然需要，通过消息系统来同步数据。
public class Cache<K,V> implements ICache<K,V>,IMsgHandler {
    // 因为机器内存是有限的，需要给本地缓存限制一个大小，比如最多保存10000条数据
    // 先限制缓存的数量大小，以后优化成设置使用的内存大小，比如最多使用50M内存
    private com.github.benmanes.caffeine.cache.Cache<K,V> cache;

    //private int size;
    private IMsgConsumer consumer;

    public Cache(CacheBuilder<K,V> builder){
        //this.size = builder.getSize();
        this.cache = builder.getCache();

        this.consumer = builder.getConsumer();
        if (null != consumer){
            this.consumer.registerHandler(this);
            this.consumer.start();
        }
    }

    @Override
    public void put(K key, V value) {
        cache.put(key,value);
    }

    @Override
    public V get(K key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void remove(K key) {
        cache.invalidate(key);
    }

    @Override
    public void handle(Message message) {
        // map delete

        // map put
    }
}
