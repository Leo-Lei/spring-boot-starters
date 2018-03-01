package com.leibangzhu.starters.cache;

public interface ICache<K,V> {
    // 添加或更新缓存
    void put(K key,V value);

    // 获取缓存
    V get(K key);

    // 删除缓存
    void remove(K key);

    // 通过Builder创建一个ICache
    static  <K,V> CacheBuilder<K,V> builder(){
        return new CacheBuilder<>();
    }
}
