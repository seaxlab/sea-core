package com.github.spy.sea.core.function.scan;

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
public interface ClassScan {

    /**
     * scan class
     *
     * @param packages
     * @return
     */
    Set<String> get(String packages);

    /**
     * scan class.
     *
     * @param packages
     * @param classLoader
     * @return
     */
    Set<String> get(String packages, ClassLoader classLoader);

    /**
     * 扫描多个包下的Class
     *
     * @param packages
     * @return
     */
    Set<String> get(List<String> packages);

    /**
     * scan class
     *
     * @param packages
     * @param classLoader
     * @return
     */
    Set<String> get(List<String> packages, ClassLoader classLoader);

    /**
     * 扫描多个包下带有注解的Class
     *
     * @param packages
     * @param anno
     * @return
     */
    Set<Class<?>> get(List<String> packages, Class<? extends Annotation> anno);

    /**
     * 获取子类型
     *
     * @param packages
     * @param customClass
     * @return
     */
    <T> Set<Class<? extends T>> getSubType(List<String> packages, Class<T> customClass);

    /**
     * 获取指定注解的方法
     *
     * @param packages
     * @param annotation
     * @return
     */
    Set<Method> getMethodsAnnotatedWith(List<String> packages, Class<? extends Annotation> annotation);
}
