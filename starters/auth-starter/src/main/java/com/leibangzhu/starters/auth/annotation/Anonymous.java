package com.leibangzhu.starters.auth.annotation;

import java.lang.annotation.*;

// http请求允许匿名访问
// http header中不需要有标识用户的token
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Anonymous {
}
