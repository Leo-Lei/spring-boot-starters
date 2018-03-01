package com.leibangzhu.starters.auth;

import com.leibangzhu.starters.auth.login.*;
import com.leibangzhu.starters.auth.login.filter.ILoginRequestHandlerFilter;
import com.leibangzhu.starters.auth.login.filter.LoginLoggingFilter;
import com.leibangzhu.starters.auth.login.filter.OutputTokenLoginHandlerFilter;
import com.leibangzhu.starters.auth.login.support.MockLoginRequestHandler;
import com.leibangzhu.starters.auth.sign.DefaultSignChecker;
import com.leibangzhu.starters.auth.sign.ISignChecker;
import com.leibangzhu.starters.auth.sign.ISignSecretProvider;
import com.leibangzhu.starters.auth.sign.support.MockSecretProvider;
import com.leibangzhu.starters.auth.token.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class AutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = ISignChecker.class)
    public ISignChecker signChecker(){
        return new DefaultSignChecker();
    }

    @Bean
    @ConditionalOnBean(value = IUsernamePasswordChecker.class)
    public ILoginRequestHandler usernamePasswordLogin(){
        return new UsernamePasswordLoginRequestHandler();
    }

    @Bean
    @ConditionalOnBean(value = IMobileVerifyCodeChecker.class)
    public ILoginRequestHandler mobileVerifyCodeLogin(){
        return new MobileVerifyCodeLoginRequestHandler();
    }

    @Bean
    @ConditionalOnMissingBean(value = ITokenCreator.class)
    public ITokenCreator tokenCreator(){
        return new DefaultTokenCreator();
    }

    @Bean
    @ConditionalOnMissingBean(value = ITokenSecretProvider.class)
    public ITokenSecretProvider tokenSecretProvider(){
        return new DefaultTokenSecretProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value = ITokenChecker.class)
    public ITokenChecker tokenChecker(){
        return new DefaultTokenChecker();
    }

    @Bean
    public Login login(){
        return new Login();
    }

    @Bean
    @Order(value = 1)
    public ILoginRequestHandlerFilter loginLoggingFilter(){
        return new LoginLoggingFilter();
    }

    @Bean
    @Order(value = 100)
    public ILoginRequestHandlerFilter outputTokenLoginHandlerFilter(){
        return new OutputTokenLoginHandlerFilter();
    }

    @Bean
    public LoginHandlerChainBuilder loginHandlerChainBuilder(){
        return new LoginHandlerChainBuilder();
    }

    @Bean
    public TokenCheckerChainBuilder tokenCheckerChainBuilder(){return new TokenCheckerChainBuilder();}

    @Bean
    @ConditionalOnMissingBean(value = ISignSecretProvider.class)
    @ConditionalOnProperty(prefix = "auth.sign.check", value = "enable", havingValue = "false",matchIfMissing = true)
    public ISignSecretProvider signSecretProvider(){
        return new MockSecretProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value = ILoginRequestHandler.class)
    @ConditionalOnProperty(prefix = "auth.login.check", value = "enable", havingValue = "false",matchIfMissing = true)
    public ILoginRequestHandler loginRequestHandler(){
        return new MockLoginRequestHandler();
    }
}
