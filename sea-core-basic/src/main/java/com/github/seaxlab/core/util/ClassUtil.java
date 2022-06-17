package com.github.seaxlab.core.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

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
            return StringUtil.EMPTY;
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
            return StringUtil.EMPTY;
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

    /**
     * load class
     *
     * @param className
     * @param initialize
     * @return
     */
    public static Class<?> load(String className, boolean initialize) {
        try {
            return Class.forName(className, initialize, getDefaultClassLoader());
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Class<?> load(String className, boolean initialize, ClassLoader classLoader) {
        Preconditions.checkNotNull(className, "class name cannot be null");
        ClassLoader clToUse = classLoader;

        if (clToUse == null) {
            clToUse = getDefaultClassLoader();
        }
        try {
            return Class.forName(className, initialize, clToUse);
        } catch (ClassNotFoundException ex) {
            throw null;
        }
    }

    /**
     * check <className> exist or not.
     *
     * @param className
     * @return
     */
    public static boolean has(String className) {
        return load(className) != null;
    }

    /**
     * check  class exist or not.
     *
     * @param className  full name.
     * @param initialize true/false.
     * @return
     */
    public static boolean has(String className, boolean initialize) {
        return load(className, initialize) != null;
    }

    /**
     * get default class loader.
     *
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl;
    }

    /**
     * resolve generic type.
     * 获取继承的泛型，泛型可能有多个，这里只取了第一个
     * <pre>
     * public class User extends Person<? extend Annotation>
     * /pre>
     *
     * @param declaredClass target class
     * @param <T>
     * @return
     */
    public static <T> Class<T> resolveGenericType(Class<?> declaredClass) {
        ParameterizedType parameterizedType = (ParameterizedType) declaredClass.getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return (Class<T>) actualTypeArguments[0];
    }


    /**
     * 判断class是否重复(不同版本的class)
     *
     * @param cls
     * @param failOnError
     * @return
     */
    public static boolean checkDuplicate(Class<?> cls, boolean failOnError) {
        return checkDuplicate(cls.getName().replace('.', '/') + ".class", failOnError);
    }

    /**
     * 检查 class是否有多个(不同版本的class)
     *
     * @param cls
     * @return
     */
    public static boolean checkDuplicate(Class<?> cls) {
        return checkDuplicate(cls, false);
    }


    /**
     * 检查class是否有多个(不同版本的class)
     *
     * @param path
     * @param failOnError
     * @return
     */
    public static boolean checkDuplicate(String path, boolean failOnError) {
        try {
            // 在ClassPath搜文件
            Enumeration<URL> urls = VersionUtil.class.getClassLoader().getResources(path);
            Set<String> files = new HashSet<String>();
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String file = url.getFile();
                    if (file != null && file.length() > 0) {
                        files.add(file);
                    }
                }
            }
            // 如果有多个，就表示重复
            if (files.size() > 1) {
                String error = "Duplicate class " + path + " in " + files.size() + " jar " + files;
                if (failOnError) {
                    throw new IllegalStateException(error);
                } else {
                    log.error(error);
                }

                return true;
            }
        } catch (Throwable e) { // 防御性容错
            log.error(e.getMessage(), e);
        }

        return false;
    }


    /**
     * 判断对象是否是基本数据类型或者基本数据类型包装类
     *
     * @param clazz Object Type class
     */
    public static boolean isSimpleType(Class<?> clazz) {
        if (clazz == String.class) {
            return true;
        } else if (clazz == int.class || clazz == Integer.class) {
            return true;
        } else if (clazz == long.class || clazz == Long.class) {
            return true;
        } else if (clazz == boolean.class || clazz == Boolean.class) {
            return true;
        } else if (clazz == byte.class || clazz == Byte.class) {
            return true;
        } else if (clazz == char.class || clazz == Character.class) {
            return true;
        } else if (clazz == short.class || clazz == Short.class) {
            return true;
        } else if (clazz == float.class || clazz == Float.class) {
            return true;
        } else if (clazz == double.class || clazz == Double.class) {
            return true;
        }
        return false;
    }


}
