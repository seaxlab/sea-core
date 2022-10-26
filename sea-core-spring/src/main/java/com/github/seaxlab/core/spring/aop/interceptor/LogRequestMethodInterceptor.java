package com.github.seaxlab.core.spring.aop.interceptor;

import com.github.seaxlab.core.spring.annotation.LogRequest;
import com.github.seaxlab.core.spring.aop.config.AopGlobalConfig;
import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * log request method interceptor
 *
 * @author spy
 * @version 1.0 2022/10/26
 * @since 1.0
 */
@Slf4j
public class LogRequestMethodInterceptor implements MethodInterceptor {

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        if (!AopGlobalConfig.getLogRequestFlag()) {
            return invocation.proceed();
        }

        LogRequest anno = AnnotatedElementUtils.findMergedAnnotation(invocation.getMethod(), LogRequest.class);
        if (anno == null) {
            return invocation.proceed();
        }

        Object returnValue = null;
        try {
            returnValue = invocation.proceed();
        } finally {
            log.info("{}, args=[{}],return={}", getPrefix(invocation, anno), invocation.getArguments(), returnValue);
        }
        //
        return returnValue;
    }

    //--------------------------------private
    private String getPrefix(MethodInvocation invocation, LogRequest anno) {
        String prefix = "";
        try {
            if (StringUtil.isBlank(anno.type())) {
                prefix = invocation.getMethod().getDeclaringClass().getSimpleName() + "." + invocation.getMethod().getName();
            } else {
                prefix = anno.type();
            }
        } catch (Exception e) {
            log.error("fail to get class and method name info", e);
        }
        //
        return prefix;
    }
}
