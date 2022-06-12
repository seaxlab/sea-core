package com.github.seaxlab.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * https://github.com/alibaba/fastjson/wiki/JSONPath
 * json path util
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public final class JSONPathUtil {

    /**
     * check contain existed path
     *
     * @param rootObject
     * @param path
     * @return
     */
    public static boolean contains(Object rootObject, String path) {
        if (rootObject instanceof JSONObject
                || rootObject instanceof JSONArray) {
        } else {
            throw new UnsupportedOperationException();
        }

        return JSONPath.contains(rootObject, path);
    }

    /**
     * get value from rootObject
     * （only for JSONObject）
     *
     * @param rootObject
     * @param path
     * @return
     */
    public static Optional<Object> get(Object rootObject, String path) {
        if (rootObject instanceof JSONObject
                || rootObject instanceof JSONArray) {
        } else {
            throw new UnsupportedOperationException();
        }

        return Optional.ofNullable(JSONPath.eval(rootObject, path));
    }


    //TODO
    // more...
}
