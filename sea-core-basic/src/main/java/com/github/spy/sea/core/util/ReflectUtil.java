package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

/**
 * 反射工具
 *
 * @author spy
 * @version 1.0 2019-06-14
 * @since 1.0
 */
@Slf4j
public final class ReflectUtil {

    /**
     * 取值
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
