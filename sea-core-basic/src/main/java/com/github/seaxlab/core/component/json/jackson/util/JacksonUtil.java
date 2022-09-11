package com.github.seaxlab.core.component.json.jackson.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.seaxlab.core.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * jackson util
 *
 * @author spy
 * @version 1.0 2022/09/04
 * @since 1.0
 */
@Slf4j
public final class JacksonUtil {

    private JacksonUtil() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 关闭空对象不让序列化功能
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * get object mapper
     * @return
     */
    //public static ObjectMapper getObjectMapper() {
    //    return objectMapper;
    //}

    /**
     * 将对象转成字符串
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static String objectToString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("fail to convert object to string", e);
        }
        return "";
    }

    /**
     * to json string
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("fail to convert object to string", e);
        }
        return "";
    }

    /**
     * 将Map转成指定的Bean
     *
     * @param map
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T mapToBean(Map map, Class<T> clazz) {
        try {
            return objectMapper.readValue(objectToString(map), clazz);
        } catch (Exception e) {
            log.error("fail to convert map to bean");
        }
        return null;
    }

    /**
     * 将Bean转成Map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> beanToMap(Object obj) {
        try {
            return objectMapper.readValue(objectToString(obj), Map.class);
        } catch (Exception e) {
            log.error("fail to convert bean to map", e);
        }
        return new HashMap<>();
    }


    /**
     * List&lt;T&gt; 转换为 List&lt;Map&lt;String, Object&gt;&gt;
     *
     * @param beans 转换对象集合
     * @return 返回转换后的 bean 列表
     */
    public static <T> List<Map<String, Object>> beansToMaps(List<T> beans) {
        if (CollectionUtil.isEmpty(beans)) {
            return Collections.emptyList();
        }
        return beans.stream().map(JacksonUtil::beanToMap).collect(toList());
    }

    /**
     * List&lt;Map&lt;String, Object&gt;&gt; 转换为 List&lt;T&gt;
     *
     * @param maps  转换 MAP 集合
     * @param clazz 对象 Class
     * @return 返回转换后的 bean 集合
     */
    public static <T> List<T> mapsToBeans(List<? extends Map<String, ?>> maps, Class<T> clazz) {
        if (CollectionUtil.isEmpty(maps)) {
            return Collections.emptyList();
        }
        return maps.stream().map(e -> mapToBean(e, clazz)).collect(toList());
    }
}
