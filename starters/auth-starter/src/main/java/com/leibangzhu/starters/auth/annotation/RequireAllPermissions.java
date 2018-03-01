package com.leibangzhu.starters.auth.annotation;

import java.lang.annotation.*;

// 必须有全部的权限才能访问
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RequireAllPermissions {
    String[] value();
}
