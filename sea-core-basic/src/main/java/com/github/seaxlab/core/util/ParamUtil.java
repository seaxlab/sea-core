package com.github.seaxlab.core.util;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * param util.
 *
 * @author spy
 * @version 1.0 2021/12/27
 * @since 1.0
 */
@Slf4j
public final class ParamUtil {

  private ParamUtil() {
  }

  /**
   * generate param str
   * <p>
   * key1=v1&key2=v2
   * </p>
   *
   * @param map
   * @return
   */
  public static String getStr(Map<String, String> map) {

    if (map == null || map.isEmpty()) {
      return "";
    }

    StringBuilder strBuilder = new StringBuilder();

    for (Map.Entry<String, String> entry : map.entrySet()) {
      strBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
    }

    return strBuilder.substring(0, strBuilder.length() - 1);
  }

}
