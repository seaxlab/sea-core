package com.github.seaxlab.core.mock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.thread.ThreadContext;
import com.github.seaxlab.core.util.EqualUtil;
import com.github.seaxlab.core.util.SetUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.google.common.base.Preconditions;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

/**
 * mock context.
 *
 * @author spy
 * @version 1.0 2020/12/16
 * @since 1.0
 */
@Slf4j
public class MockContext {

  /**
   * get raw data.
   *
   * @return raw string
   */
  public static String getRaw() {
    return ThreadContext.getSafe(CoreConst.DEFAULT_MOCK_KEY);
  }

  /**
   * get mock data
   *
   * @return data
   */
  public static String getData() {
    return StringUtil.defaultIfBlank(getRaw(), StringUtil.EMPTY);
  }

  /**
   * convert mock data to json.
   *
   * @return JSONObject
   */
  public static JSONObject getJSON() {
    String value = getRaw();
    if (StringUtil.isNotEmpty(value)) {
      if (JSON.isValidObject(value)) {
        return JSON.parseObject(value);
      } else {
        log.warn("mock data is not valid json object.");
        return new JSONObject();
      }
    }

    return new JSONObject();
  }

  /**
   * 扁平的获取
   * <pre>
   *     boolean mockFlag = MockContext.getFlag("mock_his_query_bill_list");
   * </pre>
   * please use getBool/get
   *
   * @param key mock key
   * @return true/false.
   */
  @Deprecated
  public static boolean hasFlag(String key) {
    Preconditions.checkNotNull(key, "mock key cannot be null");
    key = processKey(key);
    String value = getRaw();
    if (StringUtil.isNotEmpty(value)) {
      if (value.contains(",")) {
        Set<String> keySet = SetUtil.toSet(value.split("\\,"));
        return keySet.contains(key);
      } else {
        return EqualUtil.isEq(key, value, false);
      }
    }

    return false;
  }

  /**
   * get mock value
   *
   * @param key mock key.
   * @return
   */
  public static int getInt(String key) {
    return get(key, 0);
  }

  /**
   * get mock data string
   *
   * @param key mock key.
   * @return
   */
  public static String getString(String key) {
    return get(key, StringUtil.EMPTY);
  }

  /**
   * get mock value
   *
   * @param key mock key.
   * @return
   */
  public static boolean getBool(String key) {
    return get(key, false);
  }


  /**
   * basic get
   *
   * @param key          mock key
   * @param defaultValue default value.
   * @param <T>          entity
   * @return t
   */
  public static <T> T get(String key, T defaultValue) {
    Preconditions.checkNotNull(key, "mock key can not be null");

    key = processKey(key);
    String value = getRaw();
    if (StringUtil.isNotEmpty(value)) {
      if (JSONValidator.from(value).getType() == JSONValidator.Type.Object) {
        JSONObject jsonObj = JSON.parseObject(value);
        return (T) jsonObj.getOrDefault(key, defaultValue);
      } else {
        log.warn("mock data is not valida json object.");
        return defaultValue;
      }
    }
    return defaultValue;
  }

  // TODO more complex method sign.

  // default value
  private static String processKey(String key) {
    if (key.startsWith("sea.mock.")) {
      key = key.replace("sea.mock.", "");
    }
    return key;
  }


}
