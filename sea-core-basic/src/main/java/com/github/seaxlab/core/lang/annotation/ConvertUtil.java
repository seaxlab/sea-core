package com.github.seaxlab.core.lang.annotation;

import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2025/12/8
 * @since 1.0
 */
@Slf4j
class ConvertUtil {

  public static <T> T convert(Object obj, Class<T> targetType) {
    if (obj == null) {
      return null;
    }
    if (targetType.isInstance(obj)) {
      return targetType.cast(obj);
    } else {
      throw new ClassCastException("Cannot cast ...");
    }
  }
}
