package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串中占位符替换
 *
 * @author spy
 * @version 1.0 2019-07-04
 * @since 1.0
 */
@Slf4j
public final class TemplateUtil {

  // {key}
  private static final Pattern PATTERN_PLACEHOLDER = Pattern.compile("\\{(.*?)}");

  // ${key}
  private static final Pattern PATTERN_DOLLAR_PLACEHOLDER = Pattern.compile("\\$\\{(.*?)}");

  private TemplateUtil() {
  }

  /**
   * 替换字符串占位符, 字符串中使用{}表示占位符
   *
   * @param strPattern 字符串模板 如 "hello, {}"
   * @param args       参数
   * @return式化后的字符串
   */
  public static String format(String strPattern, Object... args) {
    return MessageFormatter.arrayFormat(strPattern, args).getMessage();
  }


  /**
   * 替换字符串占位符, 字符串中使用${key}表示占位符
   *
   * @param template 需要匹配的字符串，示例："名字:${name},年龄:${age},学校:${school}";
   * @param param    参数集,Map类型
   * @return string
   */
  public static String format(String template, Map<String, String> param) {
    if (StringUtils.isBlank(template) || MapUtils.isEmpty(param)) {
      return template;
    }

    String targetString = template;
    Matcher matcher = PATTERN_DOLLAR_PLACEHOLDER.matcher(template);
    while (matcher.find()) {
      try {
        String key = matcher.group();
        String keyClone = key.substring(2, key.length() - 1).trim();
        Object value = param.get(keyClone);
        if (value != null) {
          targetString = targetString.replace(key, value.toString());
        }
      } catch (Exception e) {
        log.error("fail to replace dollar placeholder", e);
      }
    }
    return targetString;
  }

  /**
   * 替换字符串占位符, 字符串中使用{key}表示占位符
   *
   * @param template 需要匹配的字符串，示例："名字:{name},年龄:{age},学校:{school}";
   * @param param    参数集,Map类型
   * @return string
   */
  public static String formatV2(String template, Map<String, String> param) {
    if (StringUtils.isBlank(template) || MapUtils.isEmpty(param)) {
      return template;
    }

    String targetString = template;
    Matcher matcher = PATTERN_PLACEHOLDER.matcher(template);
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


  /**
   * 使用索引{N}进行格式化信息
   *
   * @param messagePattern 模板字符串，使用{N}作为占位符,N从0开始
   * @param args           参数列表
   * @return
   */
  public static String formatByIndex(String messagePattern, Object... args) {
    return MessageFormat.format(messagePattern, args);
  }
}
