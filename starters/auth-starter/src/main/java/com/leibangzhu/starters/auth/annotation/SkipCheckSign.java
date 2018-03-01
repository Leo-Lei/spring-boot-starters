package com.leibangzhu.starters.auth.annotation;

import java.lang.annotation.*;

// http请求不需要校验签名
// http请求中不需要这些参数: appName,timestamp,sign
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface SkipCheckSign {
}
