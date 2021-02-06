package com.github.spy.sea.core.spring.aop;

import com.github.spy.sea.core.thread.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * PointCut方式只能使用常量，推荐使用MethodInterceptor
 *
 * @author spy
 * @version 1.0 2021/2/5
 * @since 1.0
 */
@Slf4j
@Aspect
public class ScheduleThreadContextAop {

    //TODO scope is large, plz notice
    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void proxyAspect() {

    }

    @Around("proxyAspect()")
    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } finally {
            ThreadContext.clean();
        }

        return result;
    }
}
