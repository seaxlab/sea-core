package com.github.spy.sea.core.bean.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * bean util
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public final class BeanUtil {

    private BeanUtil() {
    }

    /**
     * deep clone
     *
     * @param object
     * @return
     */
    public static Object deepClone(Object object) {
        if (object == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(object), object.getClass());
    }

    /**
     * deep copy.
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deepCopy(Object obj, Class<T> clazz) {
        String json = JSON.toJSONString(obj);
        return JSON.parseObject(json, clazz);
    }

    /**
     * deep copy list.
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> deepCopyList(List<?> obj, Class<T> clazz) {
        return JSON.parseArray(JSON.toJSONString(obj), clazz);
    }

}
