package com.leibangzhu.starters.auth.interceptor;

import com.leibangzhu.starters.auth.annotation.SkipCheckSign;
import com.leibangzhu.starters.auth.sign.ISignChecker;
import com.leibangzhu.starters.common.Response;
import com.leibangzhu.starters.common.Result;
import com.leibangzhu.starters.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckSignInterceptor implements HandlerInterceptor {

    @Autowired
    private ISignChecker checker;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        HandlerMethod method = (HandlerMethod) o;

        if (needCheckSign(method)){
            Result result = checker.check(httpServletRequest);
            if (!result.isSuccess()){
                Response error = Response.newBuilder().error("签名校验失败").build();
                httpServletResponse.getOutputStream().write(JsonUtil.serialize(error).getBytes());
                return false;
            }
        }

        return true;
    }

    // 判断是否需要校验签名
    // 当在application.properties中配置了auth.sign.check.enable=false,所有方法都会跳过校验签名
    // 当方法加上了@SkipCheckSign注解时，该方法会跳过校验签名
    private boolean needCheckSign(HandlerMethod method){
        SkipCheckSign skipCheckSign = method.getMethodAnnotation(SkipCheckSign.class);
        return null == skipCheckSign;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
