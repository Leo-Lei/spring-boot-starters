package com.leibangzhu.starters.auth.annotation;

import java.lang.annotation.*;

// 必须有全部的角色才能访问
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RequireAllRoles {
    String[] value();
}
