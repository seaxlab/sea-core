package com.github.seaxlab.core.spring.aop.advisor;

import com.github.seaxlab.core.spring.annotation.LogCost;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/16
 * @since 1.0
 */
@Slf4j
public class LogCostPointCut extends StaticMethodMatcherPointcut {

    @SneakyThrows
    @Override
    public boolean matches(Method method, Class<?> aClass) {
        // 直接使用spring工具包，来获取method上的注解（会找父类上的注解）
        return AnnotatedElementUtils.hasAnnotation(method, LogCost.class);
    }
}
