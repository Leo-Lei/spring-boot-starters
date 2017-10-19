package com.leibangzhu.demoa.service;


import com.leibangzhu.demoa.api.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableAutoConfiguration
@ComponentScan
public class App {

    @Autowired
    private IHelloService helloService;

    @RequestMapping("/")
    String home(){
        return "Hello world!";
    }

    @RequestMapping("/hello")
    String hello(){
        return helloService.sayHello("leo");
    }

    @RequestMapping("/hello2")
    String hello2(){
        return helloService.sayHello2("leo");
    }


    public static void main(String[] args){
        SpringApplication.run(App.class,args);
    }

}
