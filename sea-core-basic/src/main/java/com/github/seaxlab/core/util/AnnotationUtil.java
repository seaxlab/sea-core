package com.github.seaxlab.core.util;

import com.github.seaxlab.core.lang.annotation.MergedAnnotation;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Annotation util
 *
 * @author spy
 * @version 1.0 2020/12/23
 * @since 1.0
 */
@Slf4j
public final class AnnotationUtil {

  private AnnotationUtil() {
  }

  private static final String ANNOTATIONS = "annotations";
  public static final String ANNOTATION_DATA = "annotationData";


  /**
   * get merged Annotation.
   *
   * @param element        class/method/field
   * @param annotationType any annotation type
   * @param <A>
   * @return
   */
  public static <A extends Annotation> A getMergedAnnotation(AnnotatedElement element, final Class<A> annotationType) {
    MergedAnnotation mergedAnnotation = MergedAnnotation.from(element);
    return mergedAnnotation.getAnnotation(annotationType);
  }

  /**
   * has merged annotation
   *
   * @param element        class/method/field
   * @param annotationType any annotation type
   * @param <A>
   * @return
   */
  public static <A extends Annotation> boolean hasMergedAnnotation(AnnotatedElement element, final Class<A> annotationType) {
    MergedAnnotation mergedAnnotation = MergedAnnotation.from(element);
    return mergedAnnotation.hasAnnotation(annotationType);
  }


  /**
   * Get the values of this annotation's members.
   * <p>
   * Use {@link #getMap(Annotation, boolean)} to extract the values of inner annotations.
   *
   * @param annotation annotation
   * @return a Map of the values of this annotation by its member names.
   */
  public static Map<String, Object> getMap(Annotation annotation) {
    return getMap(annotation, false);
  }

  /**
   * Get the values of this annotation's members.
   * <p>
   * If {@code recursive} is true, call this method recursively on any annotation
   * instances found within the annotation's members.
   *
   * @param annotation annotation
   * @param recursive  if true, call this method recursively on inner annotations.
   * @return a Map of the values of this annotation by its member names.
   */
  public static Map<String, Object> getMap(Annotation annotation, boolean recursive) {

    Map<String, Object> result = new LinkedHashMap<>();
    Method[] methods = annotation.annotationType().getDeclaredMethods();
    Method currentMethod = null;
    try {
      for (Method method : methods) {
        currentMethod = method;
        method.setAccessible(true);
        Object value = method.invoke(annotation);
        if (recursive && value instanceof Annotation) {
          value = getMap((Annotation) value, true);
        }
        result.put(method.getName(), value);
      }
      return result;
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new IllegalStateException("Unexpected error invoking annotation member method " + currentMethod.getName() + "() on annotation: " + annotation, e);
    }
  }

  /**
   * 修改原先的注解内容（方法比较冷门）
   *
   * @param clazzToLookFor
   * @param annotationToAlter
   * @param annotationValue
   */
  public static void change(Class<?> clazzToLookFor, Class<? extends Annotation> annotationToAlter, Annotation annotationValue) {
    if (isJDK7OrLower()) {
      try {
        Field annotations = Class.class.getDeclaredField(ANNOTATIONS);
        annotations.setAccessible(true);
        Map<Class<? extends Annotation>, Annotation> map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(clazzToLookFor);
        map.put(annotationToAlter, annotationValue);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      try {
        //In JDK8 Class has a private method called annotationData().
        //We first need to invoke it to obtain a reference to AnnotationData class which is a private class
        Method method = Class.class.getDeclaredMethod(ANNOTATION_DATA, null);
        method.setAccessible(true);
        //Since AnnotationData is a private class we cannot create a direct reference to it. We will have to
        //manage with just Object
        Object annotationData = method.invoke(clazzToLookFor);
        //We now look for the map called "annotations" within AnnotationData object.
        Field annotations = annotationData.getClass().getDeclaredField(ANNOTATIONS);
        annotations.setAccessible(true);
        Map<Class<? extends Annotation>, Annotation> map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(annotationData);
        map.put(annotationToAlter, annotationValue);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static boolean isJDK7OrLower() {
    boolean jdk7OrLower = true;
    try {
      Class.class.getDeclaredField(ANNOTATIONS);
    } catch (NoSuchFieldException e) {
      //Willfully ignore all exceptions
      jdk7OrLower = false;
    }
    return jdk7OrLower;
  }


  /**
   * 根据类命名、注解类型查找类上的注解
   *
   * @param className
   * @param annotationClass
   * @param <T>
   * @return
   * @throws ClassNotFoundException
   */
  public static <T extends Annotation> Annotation findClassAnnotation(String className, Class<T> annotationClass) throws ClassNotFoundException {
    //反射使用类加载器加载类
    Class<?> type = Class.forName(className);
    return findClassAnnotation(type, annotationClass);
  }

  /**
   * 根据类类型、注解类型查找类上的注解
   *
   * @param type
   * @param annotationClass
   * @param <T>
   * @param <U>
   * @return
   * @throws ClassNotFoundException
   */
  public static <T, U extends Annotation> U findClassAnnotation(Class<T> type, Class<U> annotationClass) {
    U annotation = null;
    //判断类上面的注解是否存在
    boolean isExist = type.isAnnotationPresent(annotationClass);
    if (isExist) {
      annotation = type.getAnnotation(annotationClass);
    }
    return annotation;
  }

  /**
   * 根据类类型、注解类型查找方法上的注解
   *
   * @param className
   * @param annotationClass
   * @param <T>
   * @return
   * @throws ClassNotFoundException
   */
  public static <T extends Annotation> Annotation[] findMethodAnnotation(String className, Class<T> annotationClass) throws ClassNotFoundException {
    Class<?> type = Class.forName(className);
    return findMethodAnnotationByExist(type, annotationClass);
  }

  /**
   * 根据类类型、注解类型查找方法上的注解，使用了isAnnotationPresent();判断注解是否存在
   *
   * @param type
   * @param annotationClass
   * @param <T>
   * @param <U>
   * @return
   * @throws ClassNotFoundException
   */
  public static <T, U extends Annotation> U[] findMethodAnnotationByExist(Class<T> type, Class<U> annotationClass) {
    //获取类的所有方法
    Method[] method = type.getMethods();
    List<U> lists = new ArrayList<>();
    for (int i = 0; i < method.length; i++) {
      //判断当前方法上是否存在对应注解
      boolean isExist = method[i].isAnnotationPresent(annotationClass);
      if (isExist) {
        lists.add(method[i].getAnnotation(annotationClass));
      }
    }
    return lists.toArray((U[]) Array.newInstance(annotationClass, lists.size()));
  }

  /**
   * 根据类类型、注解类型查找方法上的注解，使用了isInstance();判断注解是否是对应注解
   *
   * @param type
   * @param annotationClass
   * @param <T>
   * @param <U>
   * @return
   * @throws ClassNotFoundException
   */
  public static <T, U extends Annotation> U[] findMethodAnnotationByIsInstance(Class<T> type, Class<U> annotationClass) {
    Method[] method = type.getMethods();
    List<U> lists = new ArrayList<>();
    for (int i = 0; i < method.length; i++) {
      //获取方法上的所有的注解遍历
      Annotation[] annotations = method[i].getAnnotations();
      for (Annotation ann : annotations) {
        //判断annotationClass注解是否ann的实例,此外还有instanceof和isAssignableFrom
        if (annotationClass.isInstance(ann)) {
          lists.add((U) ann);
        }
      }
    }
    return lists.toArray((U[]) Array.newInstance(annotationClass, lists.size()));
  }
}
