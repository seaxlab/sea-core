package com.github.spy.sea.core.component.scan;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/13
 * @since 1.0
 */
@Slf4j
public abstract class AbstractClassScan implements ClassScan {

    @Override
    public Set<String> get(String packages) {
        return null;
    }

    @Override
    public Set<String> get(String packages, ClassLoader classLoader) {
        return null;
    }

    @Override
    public Set<String> get(List<String> packages) {
        return null;
    }

    @Override
    public Set<String> get(List<String> packages, ClassLoader classLoader) {
        return null;
    }

    @Override
    public Set<Class<?>> get(List<String> packages, Class<? extends Annotation> anno) {
        return null;
    }

    @Override
    public <T> Set<Class<? extends T>> getSubType(List<String> packages, Class<T> customClass) {
        return null;
    }

    @Override
    public Set<Method> getMethodsAnnotatedWith(List<String> packages, Class<? extends Annotation> annotation) {
        return null;
    }
}
