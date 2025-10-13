package com.github.seaxlab.core.util;

import com.github.seaxlab.core.common.SymbolConst;
import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/9/16
 * @since 1.0
 */
@Slf4j
public final class UrlUtil {

  public static final String SCHEMA = "schema";
  public static final String HOST = "host";
  public static final String PORT = "port";

  /**
   * decode str
   *
   * @param str string value.
   * @return
   */
  public static String decode(String str) {
    try {
      return URLDecoder.decode(str, StandardCharsets.UTF_8.name());
    } catch (Exception e) {
      log.error("fail to decode url,", e);
    }
    return "";
  }

  /**
   * encode str
   *
   * @param str string value.
   * @return
   */
  public static String encode(String str) {
    try {
      return URLEncoder.encode(str, StandardCharsets.UTF_8.name());
    } catch (Exception e) {
      log.error("fail to encode url", e);
    }
    return "";
  }

  /**
   * build url
   *
   * @param url
   * @param key
   * @param value
   * @return
   */
  public static String build(String url, String key, String value) {
    return url + (url.indexOf('?') > 0 ? "&" : "?") + key + "=" + value;
  }

  /**
   * build url
   *
   * @param url
   * @param param
   * @return
   */
  public static String build(String url, Map<String, Object> param) {
    StringBuilder strBuilder = new StringBuilder();

    for (Entry<String, Object> elem : param.entrySet()) {
      if (StringUtil.isNotEmpty(strBuilder.toString())) {
        strBuilder.append("&");
      }
      strBuilder.append(elem.getKey())
                .append("=")
                .append(elem.getValue());
    }

    return url + (url.indexOf('?') > 0 ? "&" : "?") + strBuilder;
  }

  /**
   * join other part of url
   *
   * @param url
   * @param paths
   * @return
   */
  public static String join(final String url, String... paths) {
    StringBuilder builder = new StringBuilder();
    String first = url;
    if (url.endsWith(SymbolConst.SLASH)) {
      first = first.substring(0, first.length() - 1);
    }
    builder.append(first);

    if (paths != null) {
      for (String path : paths) {
        String item = path;
        if (StringUtil.isEmpty(item)) {
          continue;
        }

        if (!item.startsWith(SymbolConst.SLASH)) {
          item = SymbolConst.SLASH + item;
        }
        if (item.endsWith(SymbolConst.SLASH)) {
          item = item.substring(0, item.length() - 1);
        }
        builder.append(item);
      }
    }

    return builder.toString();
  }

  /**
   * parse url
   * <preview> http://ip:port?abc=1&a=2 </preview>
   *
   * @param url
   * @return
   */
  public static Properties parse(String url) {
    // ip:port?database=x11&abc=xx;
    String[] strArray = url.split("\\?");
    Properties props = new Properties();

    // schema
    String instance = strArray[0];
    if (instance.contains("://")) {
      int index = instance.indexOf("://");
      props.put(SCHEMA, instance.substring(0, index));
      // left
      instance = instance.substring(index + 3);
    }
    String[] instanceArray = instance.split(":");
    switch (instanceArray.length) {
      case 0:
        break;
      case 1:
        props.put(HOST, instanceArray[0]);
        props.put(PORT, "80");
        break;
      case 2:
        props.put(HOST, instanceArray[0]);
        props.put(PORT, instanceArray[1]);
        break;
    }

    if (strArray.length <= 1) {
      return props;
    }

    // params
    String params = strArray[1];
    String[] paramArray = params.split("&");
    for (String item : paramArray) {
      String[] itemArray = item.split("=");
      switch (itemArray.length) {
        case 0:
          break;
        case 1:
          props.put(itemArray[0], "");
          break;
        case 2:
          props.put(itemArray[0], decode(itemArray[1]));
          break;
      }
    }
    return props;
  }

  /**
   * param str to props.
   *
   * @param paramStr http param str.
   * @return
   */
  public Properties parseParamToProps(String paramStr) {
    return PropertiesUtil.parse(paramStr);
  }

  /**
   * param str to map.
   *
   * @param paramStr http param str.
   * @return
   */
  public Map<String, String> parseParamToMap(String paramStr) {
    return MapUtil.parse(paramStr);
  }


}
