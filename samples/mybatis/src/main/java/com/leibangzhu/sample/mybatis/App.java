package com.leibangzhu.sample.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {

    @Autowired
    private Foo foo;

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }

    @RequestMapping("/")
    String home(){
        String s = foo.getUser("1").getName();
        return s;
    }

}
