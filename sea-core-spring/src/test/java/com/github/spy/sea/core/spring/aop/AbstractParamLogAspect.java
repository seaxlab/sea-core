package com.github.spy.sea.core.spring.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/17
 * @since 1.0
 */
@Slf4j
public abstract class AbstractParamLogAspect {

    public Object handler(ProceedingJoinPoint joinPoint) throws Throwable {

        before(joinPoint);

        Object returnObj = joinPoint.proceed(joinPoint.getArgs());

        String result = "";
        if (returnObj != null) {
            result = JSON.toJSONString(returnObj);
        }
        log.info("请求响应:{}", result);
        return returnObj;
    }

    /**
     * before 拦截
     *
     * @param joinPoint
     */
    protected abstract void before(ProceedingJoinPoint joinPoint) throws Throwable;

}
