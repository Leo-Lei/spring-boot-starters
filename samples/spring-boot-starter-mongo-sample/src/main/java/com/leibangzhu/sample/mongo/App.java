package com.leibangzhu.sample.mongo;

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

    @RequestMapping("/")
    String home(){
        foo.addDocument();
        return foo.find().get(0).get("age").toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }

}
