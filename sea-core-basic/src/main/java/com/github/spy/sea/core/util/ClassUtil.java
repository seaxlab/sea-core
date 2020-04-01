package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;

/**
 * class util
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
@Slf4j
public final class ClassUtil {

    private ClassUtil() {
    }


    /**
     * 包含包路径的名称，如“java.lang.String”
     *
     * @param clazz
     * @return
     */
    public static String getFullClassName(Class<?> clazz) {
        if (null == clazz) {
            return null;
        }
        return clazz.getName();
    }

    /**
     * 类名，只有java.lang.String 返回"String"
     *
     * @param clazz
     * @return
     */
    public static String getClassName(Class<?> clazz) {
        if (null == clazz) {
            return null;
        }
        return clazz.getSimpleName();
    }

    /**
     * 通过实例对象获取一个类名
     *
     * @param obj
     * @return
     */
    public static String getClassName(Object obj) {
        if (null == obj) {
            return null;
        }
        return getClassName(obj.getClass());
    }

    /**
     * check obj is one of classes.
     *
     * @param obj
     * @param classes
     * @return
     */
    public static boolean isOneOfClasses(Object obj, Class<?>... classes) {
        if (obj == null) {
            return false;
        }

        if (classes == null || classes.length == 0) {
            return false;
        }

        for (int i = 0; i < classes.length; i++) {
            Class<?> clazz = classes[i];

            if (clazz.isInstance(obj)) {

            } else {
                return false;
            }
        }

        return true;
    }


    /**
     * 返回PackageName
     */
    public static String getPackageName(final Class<?> cls) {
        return ClassUtils.getPackageName(cls);
    }

    /**
     * 返回PackageName
     */
    public static String getPackageName(final String className) {
        return ClassUtils.getPackageName(className);
    }

    /**
     * 当前线程的class loader
     *
     * @return
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获得class loader<br>
     * 若当前线程class loader不存在，取当前类的class loader
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassUtil.class.getClassLoader();
        }
        return classLoader;
    }

    /**
     * load  className
     *
     * @param className
     * @return
     */
    public static Class<?> load(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
