package com.leibangzhu.starters.common;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.MessageFormat;

@Component
@Aspect
public class LoggerAspect {

    private static Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("@within(com.leibangzhu.starters.common.annotation.Loggable) || @annotation(com.leibangzhu.starters.common.annotation.Loggable)")
    public void recordLog() {

    }

    @Pointcut(" !@annotation(com.leibangzhu.starters.common.annotation.IgnoreLoggable)")
    public void notIgnoreLog() {

    }

    @Around(value = "recordLog() && notIgnoreLog()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        StringBuilder log = new StringBuilder();
        try {
            Object[] objects = joinPoint.getArgs();
            log.append(MessageFormat.format("方法:{0}.{1},参数列表:{2},",
                    method.getDeclaringClass().getCanonicalName(),
                    method.getName(),
                    JSONObject.toJSONString(objects)));
        } catch (Exception e) {
            log.append(MessageFormat.format("调用错误,{0}", e.getMessage()));
            logger.error(log.toString());
        }

        try {
            result = joinPoint.proceed();
        } catch (Exception e){
            log.append(MessageFormat.format("调用错误,{0}", e.getMessage()));
            logger.error(log.toString(),e);
        }

        try {
            String json = JSONObject.toJSONString(result);
            log.append(MessageFormat.format("返回值:{0}", json));
            logger.info(log.toString());
        } catch (Throwable throwable) {
            log.append(MessageFormat.format("调用错误,{0}", throwable.getMessage()));
            logger.error(log.toString());
        }

        return result;
    }
}