package com.hhjang.spring.study.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

/*
    Spring AOP를 이용한 UserService의 메소드 실행시 실행시간, 정보의 logging을 하기위한 Aspect
 */

@Aspect
@Component
@Slf4j
public class LoggingElapsedTimeAspect {

    // Advice, 특정한 JoinPoint에서 실행되는 Action
    @Around("userServiceLogging()")
    public Object loggingElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("logging start !!!! -----------------------------------");
        log.info("메소드 Name : " + joinPoint.getSignature().getName());
        Arrays.stream(joinPoint.getArgs()).forEach(o -> log.info("파라미터 Info : " + o.toString()));

        StopWatch stopWatch = new StopWatch(getClass().getName());
        try {
            stopWatch.start(joinPoint.getSignature().getName());
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info("Elapsed Time logging : {}", stopWatch.prettyPrint());
        }
    }

    // PointCut, JoinPoint의 부분집합으로서 실제 Advice가 실행되는 JoinPoint의 모
    @Pointcut("execution(* com.hhjang.spring.study.transaction.user.UserService*.*(..))")
    public void userServiceLogging() {}
}
