package com.github.seaxlab.core.spring.aop.advisor;

import com.github.seaxlab.core.spring.annotation.LogRequest;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

/**
 * Log request point cut
 *
 * @author spy
 * @version 1.0 2022/10/26
 * @since 1.0
 */
public class LogRequestPointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return AnnotatedElementUtils.hasAnnotation(method, LogRequest.class);
    }
}
