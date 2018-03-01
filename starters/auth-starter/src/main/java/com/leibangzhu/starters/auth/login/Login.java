package com.leibangzhu.starters.auth.login;

import com.leibangzhu.starters.common.Result;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Login implements ApplicationContextAware {

    public static Result doLogin(LoginRequest request){
        LoginHandlerChainBuilder chainBuilder = applicationContext.getBean(LoginHandlerChainBuilder.class);

        return chainBuilder.buildHandlerChain().handle(request);
    }

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

//    // 通过用户名，密码登录
//    public static UsernamePasswordLogin byUsernamePassword(){
//        return applicationContext.getBean(UsernamePasswordLogin.class);
//    }
//
//    // 通过手机号，验证码登录
//    public static MobileVerifyCodeLogin byMobileVerifyCode(){
//        return applicationContext.getBean(MobileVerifyCodeLogin.class);
//    }
}
