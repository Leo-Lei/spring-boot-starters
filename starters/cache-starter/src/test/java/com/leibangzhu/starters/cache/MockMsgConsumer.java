package com.leibangzhu.starters.cache;

import com.leibangzhu.starters.message.api.FilterOption;
import com.leibangzhu.starters.message.api.IMsgConsumer;
import com.leibangzhu.starters.message.api.IMsgHandler;

public class MockMsgConsumer implements IMsgConsumer {
    @Override
    public void start() {
        System.out.println("MockMsgConsumer started ...");

    }

    @Override
    public void registerHandler(IMsgHandler handler) {
        System.out.println("MockMsgConsumer registerHandler ...");
    }

    @Override
    public void subscribe(FilterOption filterOption) {
        System.out.println("MockMsgConsumer subscribe ...");
    }
}
