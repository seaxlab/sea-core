package com.github.seaxlab.core.util;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 占位符替换
 *
 * @author spy
 * @version 1.0 2019-07-04
 * @since 1.0
 */
@Slf4j
public final class TemplateUtil {

  private static final Pattern PATTERN_PLACEHOLDER = Pattern.compile("\\{(.*?)\\}");

  private TemplateUtil() {
  }

  /**
   * 替换字符串占位符, 字符串中使用{key}表示占位符
   *
   * @param sourceString 需要匹配的字符串，示例："名字:{name},年龄:{age},学校:{school}";
   * @param param        参数集,Map类型
   * @return
   */
  public static String replace(String sourceString, Map<String, String> param) {
    if (Strings.isNullOrEmpty(sourceString) || MapUtil.isEmpty(param)) {
      return sourceString;
    }

    String targetString = sourceString;
    Matcher matcher = PATTERN_PLACEHOLDER.matcher(sourceString);
    while (matcher.find()) {
      try {
        String key = matcher.group();
        String keyClone = key.substring(1, key.length() - 1).trim();
        Object value = param.get(keyClone);
        if (value != null) {
          targetString = targetString.replace(key, value.toString());
        }
      } catch (Exception e) {
        log.error("fail to replace", e);
      }
    }
    return targetString;
  }
}
