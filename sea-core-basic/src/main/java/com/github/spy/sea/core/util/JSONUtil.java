package com.github.spy.sea.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.spy.sea.core.model.BaseResult;
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

    private JSONUtil() {
    }

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
        return JSON.toJSONString(obj,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect);
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

    /**
     * to base result
     *
     * @param str data
     * @param <T> entity
     * @return BaseResult&lt;T&gt;
     */
    public static <T> BaseResult<T> toResult(String str, Class<T> clazz) {
        return JSON.parseObject(str, new TypeReference<BaseResult<T>>(clazz) {
        });
    }

    /**
     * to base result list.
     *
     * @param str   data
     * @param clazz class type
     * @param <T>   entity
     * @return BaseResult&lt;List&lt;T&gt;&gt;
     */
    public static <T> BaseResult<List<T>> toResultList(String str, Class<T> clazz) {
        return JSON.parseObject(str, new TypeReference<BaseResult<List<T>>>(clazz) {
        });
    }

    /**
     * convert to JSONArray
     *
     * @param str
     * @return
     */
    public static JSONArray toJSONArray(String str) {
        return JSON.parseArray(str);
    }

    /**
     * convert to object list
     *
     * @param str   string content
     * @param clazz generic class
     * @param <T>
     * @return
     */
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
        JSONObject jsonObj = JSON.parseObject(jsonObjStr);
        return jsonObj.getString(key);
    }


}
