package com.leibangzhu.starters.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.MessageFormat;


@Component
@Aspect
public class PrometheusHttpMonitorAspect {

    @Value("${spring.application.name:unknown}")
    private String appName;

    private static Logger logger = LoggerFactory.getLogger(PrometheusHttpMonitorAspect.class);

    @Pointcut("@within(com.leibangzhu.starters.prometheus.annotation.PrometheusHttpMonitor) || @annotation(com.leibangzhu.starters.prometheus.annotation.PrometheusHttpMonitor)")
    public void recordLog() {

    }

    @Pointcut(" !@annotation(com.leibangzhu.starters.prometheus.annotation.PrometheusHttpMonitorIgnore)")
    public void notIgnoreLog() {

    }

    @Around(value = "recordLog() && notIgnoreLog()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        StringBuilder log = new StringBuilder();
        HttpServletRequest request = null;
        try {
            Object[] objects = joinPoint.getArgs();
            // Get HttpServletRequest
            for (int i = 0;i< objects.length;i++){
                if (objects[i] instanceof HttpServletRequest){
                    request = (HttpServletRequest)objects[i];
                }
            }
        } catch (Exception e) {
            log.append(MessageFormat.format("调用错误,{0}", e.getMessage()));
            logger.error(log.toString());
        }

        long startTime = System.currentTimeMillis();
        result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        // 处理appName，Prometheus中的Metric Name不能有"-",将"-"转换成"_"。
        appName = appName.replace("-","_");

        Counter counter = PrometheusHttpMetricFactory.getCounter(appName);
        counter.labels(appName,request.getRequestURI()).inc();
        logger.info("Http method invoked, appName:{},URI:{}", appName, request.getRequestURI());
        Gauge gauge = PrometheusHttpMetricFactory.getGauge(appName);
        gauge.labels(appName,request.getRequestURI()).set(endTime-startTime);
        logger.info("Http method finished in elapsed:{}ms, appName:{},URI:{}",(endTime - startTime),appName,request.getRequestURI());

        return result;
    }
}
