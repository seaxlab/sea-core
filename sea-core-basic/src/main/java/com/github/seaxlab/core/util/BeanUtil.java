package com.github.seaxlab.core.util;

import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

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
    return JSONUtil.toObj(JSONUtil.toStr(object), object.getClass());
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
    return JSONUtil.toObj(JSONUtil.toStr(obj), clazz);
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
    return JSONUtil.toList(JSONUtil.toStr(obj), clazz);
  }


  /**
   * 将对象装换为 map,对象转成 map，key肯定是字符串
   *
   * @param bean 转换对象
   * @return 返回转换后的 map 对象
   */
  @SuppressWarnings("unchecked")
  public static Map<String, Object> beanToMap(Object bean) {
    //return null == bean ? null : BeanMap.create(bean);
    return Objects.isNull(bean) ? null : JacksonUtil.beanToMap(bean);
  }

  /**
   * map 转换为 java bean 对象
   *
   * @param map   转换 MAP
   * @param clazz 对象 Class
   * @return 返回 bean 对象
   */
  public static <T> T mapToBean(Map<String, ?> map, Class<T> clazz) {
    // cglib中创建的Map是不可变对象
    //T bean = ClassUtil.newInstance(clazz);
    //BeanMap.create(bean).putAll(map);
    //return bean;

    return JacksonUtil.mapToBean(map, clazz);
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
    return beans.stream().map(BeanUtil::beanToMap).collect(toList());
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
