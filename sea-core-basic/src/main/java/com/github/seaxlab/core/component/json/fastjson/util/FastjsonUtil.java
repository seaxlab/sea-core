package com.github.seaxlab.core.component.json.fastjson.util;

import static java.util.stream.Collectors.toList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.seaxlab.core.util.CollectionUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * fastjson util
 *
 * @author spy
 * @version 1.0 2022/09/04
 * @since 1.0
 */
@Slf4j
public class FastjsonUtil {

  private FastjsonUtil() {
  }

  /**
   * 将对象转成字符串
   *
   * @param obj
   * @return
   * @throws Exception
   */
  public static String objectToString(Object obj) {
    return JSON.toJSONString(obj);
  }

  /**
   * to json string
   *
   * @param obj
   * @return
   */
  public static String toString(Object obj) {
    return JSON.toJSONString(obj);
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
    return JSON.parseObject(JSON.toJSONString(map), clazz);
  }

  /**
   * 将Bean转成Map
   *
   * @param obj
   * @return
   * @throws Exception
   */
  public static Map<String, Object> beanToMap(Object obj) {
    return JSON.parseObject(JSON.toJSONString(obj), new TypeReference<Map<String, Object>>() {
    });
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
    return beans.stream().map(FastjsonUtil::beanToMap).collect(toList());
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
