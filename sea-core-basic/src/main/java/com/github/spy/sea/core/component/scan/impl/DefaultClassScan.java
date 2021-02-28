package com.github.spy.sea.core.component.scan.impl;

import com.github.spy.sea.core.component.scan.AbstractClassScan;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

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
public class DefaultClassScan extends AbstractClassScan {
    @Override
    public Set<String> get(String packages) {
        return get(packages, Thread.currentThread().getContextClassLoader());
    }

    @Override
    public Set<String> get(String packages, ClassLoader classLoader) {
        Reflections reflections = new Reflections(packages, classLoader, new SubTypesScanner(false));
        return reflections.getAllTypes();
    }

    @Override
    public Set<String> get(List<String> packages) {
        return get(packages, Thread.currentThread().getContextClassLoader());
    }

    @Override
    public Set<String> get(List<String> packages, ClassLoader classLoader) {
        Reflections reflections = new Reflections(packages, classLoader,
                new SubTypesScanner(false));
        return reflections.getAllTypes();
    }

    @Override
    public Set<Class<?>> get(List<String> packages, Class<? extends Annotation> anno) {
        Reflections reflections = new Reflections(packages);
        return reflections.getTypesAnnotatedWith(anno);
    }

    @Override
    public <T> Set<Class<? extends T>> getSubType(List<String> packages, Class<T> customClass) {
        Reflections reflections = new Reflections(packages);
        return reflections.getSubTypesOf(customClass);
    }

    @Override
    public Set<Method> getMethodsAnnotatedWith(List<String> packages, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packages);
        return reflections.getMethodsAnnotatedWith(annotation);
    }
}
