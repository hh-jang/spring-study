package com.hhjang.spring.study.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

@Aspect
@Component
@Slf4j
@Profile("!test")
public class ErrorHandlingAspect {

    @Around("userServiceErrorHandling()")
    public Object errorHandle(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("occur error!!!!" + e.toString());
            log.error("logging by aspect");
        }

        return obj;
    }

    @Pointcut("execution(* com.hhjang.spring.study.transaction.user.UserService*.*(..))")
    public void userServiceErrorHandling() {}
}
