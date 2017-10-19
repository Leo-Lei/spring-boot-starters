package com.leibangzhu.sample.common;

import com.leibangzhutech.starters.common.http.IHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {

    @Autowired
    private IHttpClient httpClientA;

    @Autowired
    private IHttpClient httpClientB;

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }

}
