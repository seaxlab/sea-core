package com.github.seaxlab.core.web.extension;

import java.util.Map;

/**
 * parse request param
 *
 * @author spy
 * @version 1.0 2020/6/1
 * @since 1.0
 */
public interface HttpRequestParseExtension {
  /**
   * get specified key
   *
   * @return Map, key:out-key, value:inner-key
   */
  Map<String, String> get();
}
