package com.github.spy.sea.core.bean.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

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

}
