package com.github.spy.sea.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.List;

/**
 * JSON util
 *
 * @author spy
 * @version 1.0 2019/8/29
 * @since 1.0
 */
@Slf4j
public final class JSONUtil {

    /**
     * check is valid json str
     *
     * @param str str
     * @return boolean
     */
    public static boolean isValid(String str) {
        return JSON.isValid(str);
    }

    /**
     * check is valid json object str
     *
     * @param str str
     * @return boolean
     */
    public static boolean isValidObject(String str) {
        return JSON.isValidObject(str);
    }

    /**
     * check is valid json array str
     *
     * @param str str
     * @return boolean
     */
    public static boolean isValidArray(String str) {
        return JSON.isValidArray(str);
    }

    /**
     * convert obj to string
     *
     * @param obj
     * @return String
     */
    public static String toStr(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static JSONObject toJSONObj(String str) {
        return JSON.parseObject(str);
    }

    public static <T> T toObj(String str, Class<T> clazz) {
        return JSON.parseObject(str, clazz);
    }

    /**
     * <pre>
     *      JSONUtil.toObj(text, new TypeReference<Result<List<User>>>() {}.getType())
     * </pre>
     *
     * @param str
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T toObj(String str, Type type) {
        return JSON.parseObject(str, type);
    }

    public static JSONArray toJSONArray(String str) {
        return JSON.parseArray(str);
    }

    public static <T> List<T> toList(String str, Class<T> clazz) {
        return JSON.parseArray(str, clazz);
    }


    /**
     * safe get key.
     *
     * @param jsonObjStr
     * @param key
     * @return
     */
    public static String getSafe(String jsonObjStr, String key) {
        try {
            return get(jsonObjStr, key);
        } catch (Exception e) {
            log.error("fail to get from jsonObj", e);
            return StringUtil.EMPTY;
        }
    }

    /**
     * get key for simple json str
     *
     * @param jsonObjStr
     * @param key
     * @return
     */
    public static String get(String jsonObjStr, String key) {
        if (!isValidObject(jsonObjStr)) {
            return StringUtil.EMPTY;
        }
        JSONObject jsonObj = JSONObject.parseObject(jsonObjStr);
        return jsonObj.getString(key);
    }


}
