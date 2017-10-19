package com.leibangzhu.starters.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AutoConfiguration {
    //扫描该项目中的@Component，目前是为了扫描LogAspect这个组件,该类里使用了AOP相关的注解，只能使用@Component来注入到Spring容器中
    //基本类库中应避免使用@Component来注入组件，应该使用@Configuration和@Bean来注入，但LogAspect是个特例。。。
    //请不要修改和删除该类，除非你知道你在做什么

}
