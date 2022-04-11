package com.example.asuredelete.aop;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class ExcuteTime  {
    @Pointcut("@annotation(com.example.asuredelete.aop.EXCTime)")
    public void showTime(){}

    @Around("showTime()")
    @SneakyThrows
    public Object expTime(ProceedingJoinPoint point){
        String name = point.getSignature().getName();
        long start=System.currentTimeMillis();
        point.proceed();
        long end=System.currentTimeMillis();
        String time = String.valueOf(end - start);
        log.info(name+":{}",time);
        return (Object) time;
    }
}
