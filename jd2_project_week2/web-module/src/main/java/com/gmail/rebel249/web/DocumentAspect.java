package com.gmail.rebel249.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Aspect
@Component
public class DocumentAspect {

    private static final Logger logger = LogManager.getLogger(DocumentAspect.class);

    @Pointcut("execution(public * com.gmail.rebel249.service.impl.DocumentServiceImpl.*(..))")
    public void callServicePublicMethod() {
    }

    @Around("callServicePublicMethod()")
    public Object aroundServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startMethod = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endMethod = System.currentTimeMillis();
        long timeOfExecution = endMethod - startMethod;
        logger.info(String.format("Method %s started at %s", joinPoint.getSignature().getName(), startMethod));
        logger.info(String.format("Method %s ended at %s", joinPoint.getSignature().getName(), endMethod));
        logger.info(String.format("Method %s executed for %d millis.",
                joinPoint.getSignature().getName(), timeOfExecution));
        return proceed;
    }
}
