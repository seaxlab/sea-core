package com.github.seaxlab.core.spring.util;

import com.google.common.base.Strings;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;

/**
 * 资源文件辅助类（默认取中文）
 *
 * @author spy
 * @version 1.0 2019-07-28
 * @since 1.0
 */
@Slf4j
public class MessageSourceUtil {

  private MessageSourceUtil() {

  }

  /**
   * 获取资源文件
   *
   * @param messageSource
   * @param code
   * @return
   */
  public static String get(MessageSource messageSource, String code) {
    return get(messageSource, code, null);
  }

  /**
   * 获取资源文件
   *
   * @param messageSource
   * @param code
   * @param message
   * @return
   */
  public static String get(MessageSource messageSource, String code, String message) {
    String desc = message;
    if (Strings.isNullOrEmpty(message)) {
      desc = messageSource.getMessage(code, null, code, Locale.CHINESE);
    }
    return desc;
  }

}
