package com.leibangzhu.sample.mybatis;

import com.leibangzhu.sample.mybatis.mapper.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Foo {

    @Autowired
    private IUserDao dao;

    public User getUser(String id){
        return dao.getById(id);
    }
}
