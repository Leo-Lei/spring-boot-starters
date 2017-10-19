package com.leibangzhu.demoa.service.service;

import com.leibangzhu.demoa.api.IHelloService;
import com.leibangzhutech.starters.common.annotation.IgnoreLoggable;
import com.leibangzhutech.starters.common.annotation.Loggable;


@Loggable
@com.alibaba.dubbo.config.annotation.Service
public class HelloService implements IHelloService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }

    @IgnoreLoggable
    @Override
    public String sayHello2(String name) {
        return "Hello, " + name;
    }


}
