package com.leibangzhu.starters.auth.annotation;

import java.lang.annotation.*;

// 有其中一个权限就可以访问
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RequireOnePermission {
    String[] value();
}
