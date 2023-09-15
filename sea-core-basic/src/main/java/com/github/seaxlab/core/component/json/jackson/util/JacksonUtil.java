package com.github.seaxlab.core.component.json.jackson.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.seaxlab.core.util.CollectionUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

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

  // thread-safe
  private static final JsonMapper objectMapper;

  static {
    objectMapper = JsonMapper.builder()
                             //忽略Transient字段
                             .enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER) //
                             .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false) //
                             .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)//
                             .build();
    // 关闭空对象不让序列化功能
    //objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    //objectMapper.enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER); //忽略transient字段，enable方法标注废弃
    //objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

    //null字段不序列化
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    //objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
    // 这里不能使用setDateFormat(),否则会导致线程不安全
    //SimpleDateFormat dateFormat = DateUtil.getSdf(DateFormatEnum.yyyy_MM_dd_HH_mm_ss);
    //objectMapper.setDateFormat(dateFormat);
    // 这里注册会影响@JsonFormat
    //objectMapper.registerModule(new SimpleModule() {
    //    {
    //        addSerializer(Date.class, new DateToStringSerializer());
    //    }
    //});
  }

  //public static ObjectMapper getObjectMapper() {
  //    return objectMapper;
  //}

  /**
   * check json str is valid.
   *
   * @param json
   * @return
   */
  public static boolean isValid(String json) {
    try {
      objectMapper.readTree(json);
    } catch (JacksonException e) {
      //JacksonException is top exception to jackson.
      log.warn("fail to validate json str");
      return false;
    }
    return true;

  }


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
    } catch (JsonProcessingException e) {
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
    } catch (JsonProcessingException e) {
      log.error("fail to convert object to string", e);
    }
    return "";
  }

  /**
   * json array string to list
   *
   * @param jsonArrayStr
   * @param <T>
   * @return
   */
  public static <T> List<T> toList(String jsonArrayStr, Class<T> clazz) {
    try {
      //DOC not work
      //objectMapper.readValue(json, new TypeReference<List<T>>() {
      //});

      JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
      return objectMapper.readValue(jsonArrayStr, javaType);
    } catch (JsonProcessingException e) {
      log.error("fail to convert json string to list", e);
    }
    return new ArrayList<>();
  }

  /**
   * json array string to List
   *
   * @param jsonArrayStr
   * @return
   */
  public static List<Map<String, Object>> toMapList(String jsonArrayStr) {
    try {
      return objectMapper.readValue(jsonArrayStr, List.class);
    } catch (JsonProcessingException e) {
      log.error("fail to convert json string to list<Map<String,Object>>", e);
    }
    return new ArrayList<>();
  }

  /**
   * json object string to Object
   *
   * @param jsonObjectStr
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T> T toObject(String jsonObjectStr, Class<T> clazz) {
    try {
      return objectMapper.readValue(jsonObjectStr, clazz);
    } catch (JsonProcessingException e) {
      log.error("fail to convert json string to Object", e);
    }
    return null;
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
      log.error("fail to convert map to bean", e);
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
    return beans.stream().map(JacksonUtil::beanToMap).collect(Collectors.toList());
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
    return maps.stream().map(e -> mapToBean(e, clazz)).collect(Collectors.toList());
  }
}
