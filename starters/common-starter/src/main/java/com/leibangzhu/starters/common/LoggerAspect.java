package com.leibangzhu.starters.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class LoggerAspect {

    private static Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("@within(com.leibangzhu.starters.common.annotation.Loggable) || @annotation(com.leibangzhu.starters.common.annotation.Loggable)")
    public void recordLog() {

    }

    @Pointcut(" !@annotation(com.leibangzhu.starters.common.annotation.IgnoreLoggable)")
    public void notIgnoreLog(){

    }

    @Around(value = "recordLog() && notIgnoreLog()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        try {
            Object[] objects = joinPoint.getArgs();
            //String context = null;
            //JSONObject.toJSONString(objects);
            if (ArrayUtils.isNotEmpty(objects)) {
                logger.info("方法:[{}], 参数列表：[{}]", method.getName(), JSONObject.toJSONString(objects));
            }else{
                logger.info("方法:[{}], 无参数列表", method.getName());
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        result = joinPoint.proceed();

        try{
            if (null != result) {
                String json = JSONObject.toJSONString(result);
                logger.info("方法:[{}], 返回值:[{}]", method.getName(), json);
            }
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage());
        }

        return result;
    }
}