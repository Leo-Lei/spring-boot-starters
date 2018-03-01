package com.leibangzhu.starters.auth.interceptor;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.auth.WebContext;
import com.leibangzhu.starters.auth.annotation.Anonymous;
import com.leibangzhu.starters.auth.token.TokenCheckerChainBuilder;
import com.leibangzhu.starters.common.Response;
import com.leibangzhu.starters.common.Result;
import com.leibangzhu.starters.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenCheckerChainBuilder tokenCheckerChainBuilder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HandlerMethod method = (HandlerMethod) o;

        // 判断是否登录，通过检查token来判断
        if (needCheckToken(method)){

            Result<Principal> result = tokenCheckerChainBuilder.buildChain().check(httpServletRequest);
            if (!result.isSuccess()){
                Response error = Response.newBuilder().error(result.getErrorCode(),result.getErrorMsg()).build();
                httpServletResponse.getOutputStream().write(JsonUtil.serialize(error).getBytes());
                return false;
            }
            WebContext.set(result.getResult());
        }

        return true;
    }

    private boolean needCheckToken(HandlerMethod method){
        Anonymous anonymous = method.getMethodAnnotation(Anonymous.class);
        return null == anonymous;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
