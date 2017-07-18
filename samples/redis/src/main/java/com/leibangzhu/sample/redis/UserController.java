package com.leibangzhu.sample.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;


    @RequestMapping(value = { "/user/set" }, method = RequestMethod.GET)
    public String setUser() throws Exception {
        repository.add(new User("tom",23));
        return "OK";
    }

    @RequestMapping(value = { "/user/get" }, method = RequestMethod.GET)
    public User getUser() throws Exception {
        return repository.get("tom");
    }

    @RequestMapping(value = { "/user/get2" }, method = RequestMethod.GET)
    public User getUser2() throws Exception {
        return repository.getByName("john");
    }

}
