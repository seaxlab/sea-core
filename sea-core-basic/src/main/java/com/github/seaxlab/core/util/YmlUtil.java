package com.github.seaxlab.core.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/14
 * @since 1.0
 */
@Slf4j
public final class YmlUtil {

  /**
   * 读取指定流的，转换成对象
   *
   * @param targetClass 目标类型
   * @param in          文件流(单个文档)
   * @param <T>
   * @return
   */
  public static <T> T load(Class<T> targetClass, InputStream in) {
    Yaml yaml = new Yaml(new Constructor(targetClass));
    T target = yaml.load(in);
    return target;
  }

  /**
   * 一个文档中加载多个yaml模块
   * classpath:yaml/test-multi.yml
   *
   * @param targetClass
   * @param in
   * @param <T>
   * @return
   */
  public static <T> List<T> loadAll(Class<T> targetClass, InputStream in) {
    Yaml yaml = new Yaml(new Constructor(targetClass));
    Iterable targets = yaml.loadAll(in);
    return Lists.newArrayList(targets);
  }

  /**
   * dump POJO to string
   *
   * @param data
   * @return
   */
  public static String dump(Object data) {
    Yaml yaml = new Yaml();
    return yaml.dump(data);
  }
}
