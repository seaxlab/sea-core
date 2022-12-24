package com.github.seaxlab.core.lang.jvm.classloader;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/8/25
 * @since 1.0
 */
@Slf4j
public class SharedClassList {

  private ConcurrentHashMap<String, Class<?>> classes;

  public SharedClassList() {
    classes = new ConcurrentHashMap<>();
  }

  public void put(String fullName, Class<?> clazz) {
    log.info("add shared class {}", fullName);
    classes.put(fullName, clazz);
  }

  public Class<?> get(String fullName) {
    return classes.containsKey(fullName) ? classes.get(fullName) : null;
  }

  public void clear() {
    if (classes != null) {
      classes.clear();
    }
  }
}
