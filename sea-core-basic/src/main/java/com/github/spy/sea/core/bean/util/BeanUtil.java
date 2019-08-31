package com.github.spy.sea.core.bean.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * bean util
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
public class BeanUtil {

    private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);


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
        return JSONObject.parseObject(JSON.toJSONString(object), object.getClass());
    }

}
