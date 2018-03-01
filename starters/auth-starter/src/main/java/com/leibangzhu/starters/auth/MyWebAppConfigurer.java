package com.leibangzhu.starters.auth;

import com.leibangzhu.starters.auth.interceptor.CheckPermissionInterceptor;
import com.leibangzhu.starters.auth.interceptor.CheckTokenInterceptor;
import com.leibangzhu.starters.auth.interceptor.CheckSignInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Value("${auth.sign.check.enable:false}")
    private String enableSignCheck;

    @Value("${auth.login.check.enable:false}")
    private String enableLoginCheck;

    @Bean
    public HandlerInterceptor checkTokenInterceptor(){
        return new CheckTokenInterceptor();
    }

    @Bean
    public HandlerInterceptor checkSighInterceptor(){
        return new CheckSignInterceptor();
    }

    @Bean
    public HandlerInterceptor checkPermissionInterceptor(){
        return new CheckPermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println(enableSignCheck);

        // 添加检查签名的拦截器
        if ("true".equalsIgnoreCase(enableSignCheck)){
            System.out.println("Add checking sign interceptor...");
            registry.addInterceptor(checkSighInterceptor()).addPathPatterns("/**");
        }

        // 添加检查token的拦截器
        if ("true".equalsIgnoreCase(enableLoginCheck)){
            System.out.println("Add checking token interceptor...");
            registry.addInterceptor(checkTokenInterceptor()).addPathPatterns("/**");
        }

        System.out.println("Add checking permission interceptor...");
        registry.addInterceptor(checkPermissionInterceptor()).addPathPatterns("/**");

        super.addInterceptors(registry);
    }
}
