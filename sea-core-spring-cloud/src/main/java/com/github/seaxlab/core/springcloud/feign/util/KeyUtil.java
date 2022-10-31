package com.github.seaxlab.core.springcloud.feign.util;

import feign.Request;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/7/1
 * @since 1.0
 */
@Slf4j
public final class KeyUtil {

  /**
   * get unified key
   *
   * @param httpMethod
   * @param url
   * @return
   */
  public static String getKey(String httpMethod, String url) {
    if (httpMethod == null) {
      httpMethod = "";
    }
    if (url == null) {
      url = "";
    }
    return httpMethod.trim().toLowerCase() + "_" + url.trim().toLowerCase();
  }

  /**
   * get unified key
   *
   * @param request
   * @return
   */
  public static String getKey(Request request) {
    if (request == null) {
      return "";
    }
    return getKey(request.httpMethod().name(), request.url());
  }


}
