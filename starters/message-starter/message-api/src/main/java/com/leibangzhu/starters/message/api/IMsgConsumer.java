package com.leibangzhu.starters.message.api;

public interface IMsgConsumer {
    void start();
    void registerHandler(IMsgHandler handler);
    void subscribe(FilterOption filterOption);
}
