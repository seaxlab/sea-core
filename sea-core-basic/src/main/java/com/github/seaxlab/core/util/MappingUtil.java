package com.github.seaxlab.core.util;

import com.github.seaxlab.core.exception.Precondition;

import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

/**
 * mapping util
 * <p>
 * (source,target)
 * </p>
 *
 * @author spy
 * @version 1.0 2024/1/5
 * @since 1.0
 */
@Slf4j
public final class MappingUtil {

  /**
   * get target value by source
   *
   * @param map
   * @param source
   * @return
   */
  public static String getBySource(Map<String, String> map, String source) {
    Precondition.checkNotNull(map);
    return map.getOrDefault(source, "");
  }

  /**
   * get source value by target
   *
   * @param map
   * @param target
   * @return
   */
  public static String getByTarget(Map<String, String> map, String target) {
    Precondition.checkNotNull(map);
    Precondition.checkNotEmpty(target, "target cannot be empty");
    for (Entry<String, String> entry : map.entrySet()) {
      if (target.equalsIgnoreCase(entry.getValue())) {
        return entry.getKey();
      }
    }
    return "";
  }
}
