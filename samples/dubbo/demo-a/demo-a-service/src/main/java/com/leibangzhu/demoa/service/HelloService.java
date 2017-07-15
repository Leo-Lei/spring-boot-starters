package com.leibangzhu.demoa.service;

import com.leibangzhu.demoa.api.IHelloService;

@com.alibaba.dubbo.config.annotation.Service
public class HelloService implements IHelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
