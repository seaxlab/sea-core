package com.github.seaxlab.core.util;

import com.github.seaxlab.core.enums.RegExpEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/19
 * @see cn.hutool.http.HtmlUtil
 * @since 1.0
 */
@Slf4j
public final class HtmlUtil {

  private HtmlUtil() {
  }


  /**
   * 清除所有HTML标签，但是不删除标签内的内容
   *
   * @param content 文本
   * @return 清除标签后的文本
   */
  public static String cleanHtmlTag(String content) {
    return content.replaceAll(RegExpEnum.HTML_MARK.getExpression(), "");
  }


  //
  ///**
  // * 过滤HTML文本，防止XSS攻击
  // *
  // * @param htmlContent HTML内容
  // * @return 过滤后的内容
  // */
  //public static String filter(String htmlContent) {
  //  return cn.hutool.http.HtmlUtil.filter(htmlContent);
  //}
}
