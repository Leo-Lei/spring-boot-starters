package com.leibangzhu.demob;

import com.alibaba.dubbo.config.annotation.Reference;
import com.leibangzhu.demoa.api.IHelloService;
import org.springframework.stereotype.Component;

@Component
public class Foo {

    @Reference
    private IHelloService helloService;

    public String hello(){
        return helloService.sayHello("foo");
    }
}
