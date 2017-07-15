package com.leibangzhu.sample.mybatis.mapper;

import com.leibangzhu.sample.mybatis.User;
import org.apache.ibatis.annotations.Param;

public interface IUserDao {
    User getById(@Param("id") String id);
}
