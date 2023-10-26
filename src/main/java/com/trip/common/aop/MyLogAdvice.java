package com.trip.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class MyLogAdvice {

    @Pointcut("@annotation(com.trip.common.annotation.MyLog)")
    public void myLog(){}

    @Pointcut("@annotation(com.trip.common.annotation.MyErrorLog)")
    public void myErrorLog(){}

    @AfterReturning("myLog()")
    public void logAdvice(JoinPoint jp) throws Exception {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        log.debug("methodName : [{}] methodClass : [{}]",method.getName(),method.getClass());
    }

    @Before("myErrorLog()")
    public void errorLogAdvice(JoinPoint jp) throws Exception {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            if(arg instanceof Exception){
                Exception e = (Exception) arg;
                log.error("errorLogAdvice > ERROR > {}",e.getMessage());
                log.error(String.valueOf(e.getStackTrace()));
            }
        }
    }
}