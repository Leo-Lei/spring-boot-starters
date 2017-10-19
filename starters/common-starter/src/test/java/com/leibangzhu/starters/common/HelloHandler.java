package com.leibangzhu.starters.common;

import com.leibangzhu.starters.common.eventaggregator.BaseEventHandler;

import java.util.Map;

public class HelloHandler extends BaseEventHandler {

    @Override
    public void handle(Map<String, Object> params) {
        System.out.println("hello");
    }
}
