package com.github.spy.sea.core.spring.aop.interceptor;

import com.github.spy.sea.core.spring.annotation.LogCost;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/15
 * @since 1.0
 */
@Slf4j
public class LogCostMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        LogCost anno = invocation.getMethod().getAnnotation(LogCost.class);
        if (anno == null) {
            return invocation.proceed();
        }

        long start = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            String prefix;
            if (StringUtil.isBlank(anno.remark())) {
                prefix = invocation.getMethod().getDeclaringClass().getSimpleName() + "." + invocation.getMethod().getName();
            } else {
                prefix = anno.remark();
            }
            log.info("{}, cost={}ms", prefix, System.currentTimeMillis() - start);
        }

    }
}
