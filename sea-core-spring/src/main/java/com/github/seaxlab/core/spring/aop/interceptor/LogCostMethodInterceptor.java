package com.github.seaxlab.core.spring.aop.interceptor;

import com.github.seaxlab.core.spring.annotation.LogCost;
import com.github.seaxlab.core.spring.aop.config.AopGlobalConfig;
import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotatedElementUtils;

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

        if (!AopGlobalConfig.getLogCostFlag()) {
            return invocation.proceed();
        }

        //boolean hasFlag = AnnotatedElementUtils.hasAnnotation(invocation.getMethod(), LogCost.class);
        //LogCost anno = invocation.getMethod().getAnnotation(LogCost.class);

        LogCost anno = AnnotatedElementUtils.findMergedAnnotation(invocation.getMethod(), LogCost.class);

        if (anno == null) {
            return invocation.proceed();
        }

        long start = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            String prefix = "";
            try {
                if (StringUtil.isBlank(anno.remark())) {
                    prefix = invocation.getMethod().getDeclaringClass().getSimpleName() + "." + invocation.getMethod().getName();
                } else {
                    prefix = anno.remark();
                }
            } catch (Exception e) {
                log.error("fail to get class and method name info", e);
            }
            log.info("{}, cost={}ms", prefix, System.currentTimeMillis() - start);
        }

    }
}
