package com.example.asuredelete.aop;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.Target;

@Aspect
@Configuration
@Slf4j
public class ExcuteTime {
    @Pointcut("@annotation(com.example.asuredelete.aop.EXCTime)")
    public void showTime(){}

    @Around("showTime()")
    @SneakyThrows
    public long expTime(ProceedingJoinPoint point){
        final String name = point.getSignature().getName();
        long start=System.currentTimeMillis();
        point.proceed();
        long end=System.currentTimeMillis();
        log.info(name+"+{}",end-start);
        return end-start;
    }
}
