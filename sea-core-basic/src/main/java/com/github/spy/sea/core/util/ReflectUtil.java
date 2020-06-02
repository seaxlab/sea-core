package com.github.spy.sea.core.util;

import com.github.spy.sea.core.common.CoreErrorConst;
import com.github.spy.sea.core.exception.ExceptionHandler;
import com.github.spy.sea.core.function.Fn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 反射工具
 *
 * @author spy
 * @version 1.0 2019-06-14
 * @since 1.0
 */
@Slf4j
public final class ReflectUtil {

    private ReflectUtil() {
    }


    /**
     * @param cls
     * @return
     */
    public static List<Field> getAllFieldsList(final Class<?> cls) {
        return FieldUtils.getAllFieldsList(cls);
    }

    /**
     * read field value from target
     *
     * @param target
     * @param fieldName
     * @return
     */
    public static Object read(final Object target, final String fieldName) {
        try {
            return FieldUtils.readField(target, fieldName, true);
        } catch (IllegalAccessException e) {
            log.error("读取值失败", e);
        }
        return null;
    }

    /**
     * 取String
     *
     * @param target
     * @param fieldName
     * @return
     */
    public static String readAsString(final Object target, final String fieldName) {
        Object obj = read(target, fieldName);

        return obj == null ? null : obj.toString();
    }


    /**
     * 赋值
     *
     * @param target
     * @param fieldName
     * @param value
     */
    public static void write(final Object target, final String fieldName, final Object value) {
        try {
            Field field = FieldUtils.getField(target.getClass(), fieldName, true);

            if (field == null) {
                log.warn("对象{}的{}属性不存在", target, fieldName);
                return;
            }

            Object finalValue = simpleConvertValue(field, value);


            FieldUtils.writeField(target, fieldName, finalValue, true);
        } catch (IllegalAccessException e) {
            log.error("赋值失败", e);
        }
    }

    private static final Pattern GET_PATTERN = Pattern.compile("^get[A-Z].*");
    private static final Pattern IS_PATTERN = Pattern.compile("^is[A-Z].*");

    /**
     * get field name of class from function
     * <note>
     * T is Object class, field should be Object class , not primitive type(such as int/long/dubbo and so on).
     * </note>
     *
     * @param fn custom function serialize
     * @return
     */
    public static <T> String getFieldName(Fn<T, Object> fn) {
        try {
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
            String getter = serializedLambda.getImplMethodName();
            if (GET_PATTERN.matcher(getter).matches()) {
                getter = getter.substring(3);
            } else if (IS_PATTERN.matcher(getter).matches()) {
                getter = getter.substring(2);
            }
            return Introspector.decapitalize(getter);
        } catch (ReflectiveOperationException e) {
            log.error("reflective operation exception", e);
            ExceptionHandler.publish(CoreErrorConst.SYS_REFLECT_OPERATION_ERR);
        }
        return null;
    }


    /**
     * invoke method
     *
     * @param object
     * @param method
     * @return
     */
    public static Object invokeMethod(Object object, String method) {
        try {
            return MethodUtils.invokeMethod(object, method);
        } catch (Exception e) {
            log.error("invoke method error", e);
            ExceptionHandler.publish(CoreErrorConst.SYS_REFLECT_OPERATION_ERR);
        }
        return null;
    }

    /**
     * invoke method
     *
     * @param object
     * @param forceAccess
     * @param method
     * @return
     */
    public static Object invokeMethod(Object object, boolean forceAccess, String method) {
        try {
            return MethodUtils.invokeMethod(object, forceAccess, method);
        } catch (Exception e) {
            log.error("invoke method error", e);
            ExceptionHandler.publish(CoreErrorConst.SYS_REFLECT_OPERATION_ERR);
        }
        return null;
    }


    /**
     * 基本类型的进行转换，避免无意义的异常
     *
     * @param field
     * @param value
     * @return
     */
    private static Object simpleConvertValue(Field field, final Object value) {
        Object finalValue = value;
        if (field.getType().isAssignableFrom(String.class)) {

            if (value.getClass().isAssignableFrom(Long.class)
                    || value.getClass().isAssignableFrom(Integer.class)
            ) {
                finalValue = value.toString();
            }

        } else if (field.getType().isAssignableFrom(Long.class)
                || value.getClass().isAssignableFrom(Integer.class)) {

            if (value.getClass().isAssignableFrom(String.class)) {
                finalValue = Long.valueOf(value.toString());
            }
        } else if (field.getType().isAssignableFrom(Integer.class)) {
            if (value.getClass().isAssignableFrom(String.class)) {
                finalValue = Integer.valueOf(value.toString());
            }
        }

        return finalValue;
    }


}
