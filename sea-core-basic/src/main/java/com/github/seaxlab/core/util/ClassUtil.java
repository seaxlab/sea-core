package com.github.seaxlab.core.util;

import com.github.seaxlab.core.exception.ExceptionHandler;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
   * Maps primitive {@code Class}es to their corresponding wrapper {@code Class}.
   */
  private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<>();

  static {
    primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
    primitiveWrapperMap.put(Byte.TYPE, Byte.class);
    primitiveWrapperMap.put(Character.TYPE, Character.class);
    primitiveWrapperMap.put(Short.TYPE, Short.class);
    primitiveWrapperMap.put(Integer.TYPE, Integer.class);
    primitiveWrapperMap.put(Long.TYPE, Long.class);
    primitiveWrapperMap.put(Double.TYPE, Double.class);
    primitiveWrapperMap.put(Float.TYPE, Float.class);
    primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
  }

  /**
   * Maps wrapper {@code Class}es to their corresponding primitive types.
   */
  private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<>();

  static {
    for (final Map.Entry<Class<?>, Class<?>> entry : primitiveWrapperMap.entrySet()) {
      final Class<?> primitiveClass = entry.getKey();
      final Class<?> wrapperClass = entry.getValue();
      if (!primitiveClass.equals(wrapperClass)) {
        wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
      }
    }
  }


  /**
   * <p>
   * 根据指定的 class ， 实例化一个对象，根据构造参数来实例化
   * </p>
   * <p>
   * 在 java9 及其之后的版本 Class.newInstance() 方法已被废弃
   * </p>
   *
   * @param clazz 需要实例化的对象
   * @param <T>   类型，由输入类型决定
   * @return 返回新的实例
   */
  public static <T> T newInstance(Class<T> clazz) {
    try {
      Constructor<T> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      return constructor.newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
             NoSuchMethodException e) {
      ExceptionHandler.publishMsg("class new instance error.");
    }
    return null;
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

    for (Class<?> clazz : classes) {
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
   * 获得class loader<br> 若当前线程class loader不存在，取当前类的class loader
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
      log.warn("class not found,{}", className);
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
      log.warn("class not found,{}", className);
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
      log.warn("class not found,{}", className);
    }
    return null;
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
   * <p>
   * 获取继承的泛型，泛型可能有多个，这里只取了第一个
   * </p>
   * <pre>
   * public class User extends Person<? extend Annotation>
   * </pre>
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
      Set<String> files = new HashSet<>();
      while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
        if (url != null) {
          String file = url.getFile();
          if (file != null && !file.isEmpty()) {
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
   * check it is a primitive wrapper
   *
   * @param type
   * @return
   */
  public static boolean isPrimitiveOrWrapper(final Class<?> type) {
    if (type == null) {
      return false;
    }
    return type.isPrimitive() || isPrimitiveWrapper(type);
  }

  /**
   * Returns whether the given {@code type} is a primitive wrapper ({@link Boolean}, {@link Byte}, {@link Character},
   * {@link Short}, {@link Integer}, {@link Long}, {@link Double}, {@link Float}).
   *
   * @param type The class to query or null.
   * @return true if the given {@code type} is a primitive wrapper ({@link Boolean}, {@link Byte}, {@link Character},
   * {@link Short}, {@link Integer}, {@link Long}, {@link Double}, {@link Float}).
   * @since 3.1
   */
  public static boolean isPrimitiveWrapper(final Class<?> type) {
    return wrapperPrimitiveMap.containsKey(type);
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
    } else {
      return false;
    }
  }


}
