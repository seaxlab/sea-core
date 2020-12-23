package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
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

    private static final String ANNOTATIONS = "annotations";
    public static final String ANNOTATION_DATA = "annotationData";

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
            throw new IllegalStateException("Unexpected error invoking annotation member method " +
                    currentMethod.getName() + "() on annotation: " + annotation, e);
        }
    }

    /**
     * 修改原先的注解内容（方法比较冷门）
     *
     * @param clazzToLookFor
     * @param annotationToAlter
     * @param annotationValue
     */
    public static void change(Class clazzToLookFor, Class<? extends Annotation> annotationToAlter,
                              Annotation annotationValue) {
        if (isJDK7OrLower()) {
            try {
                Field annotations = Class.class.getDeclaredField(ANNOTATIONS);
                annotations.setAccessible(true);
                Map<Class<? extends Annotation>, Annotation> map =
                        (Map<Class<? extends Annotation>, Annotation>) annotations.get(clazzToLookFor);
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
                Map<Class<? extends Annotation>, Annotation> map =
                        (Map<Class<? extends Annotation>, Annotation>) annotations.get(annotationData);
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
}
