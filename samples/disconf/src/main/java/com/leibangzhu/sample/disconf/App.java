package com.leibangzhu.sample.disconf;

import com.leibangzhu.sample.disconf.config.FooConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class App {

    @Autowired
    private FooConfig fooConfig;

    @RequestMapping("/")
    String home(){
        return fooConfig.getScore().toString();
    }


    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
