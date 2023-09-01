package com.github.seaxlab.core.util;

import com.github.seaxlab.core.enums.RegExpEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * 常用正则
 *
 * @author spy
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public final class RegExpUtil {

  /**
   * check by RegExpEnum
   *
   * @param text
   * @param regExpEnum
   * @return
   */
  public static boolean is(String text, RegExpEnum regExpEnum) {
    Pattern pattern = Pattern.compile(regExpEnum.getExpression());
    return pattern.matcher(text).matches();
  }

  public static boolean isNot(String text, RegExpEnum regExpEnum) {
    return !is(text, regExpEnum);
  }

  /**
   * 根据传入的正则表达式和字符串进行校验。<br/>
   *
   * @param text       内容
   * @param patternStr 正则表达式
   * @return 如果是返回true，不是返回false
   */
  public static boolean is(String text, String patternStr) {
    Pattern pattern = Pattern.compile(patternStr);
    return pattern.matcher(text).matches();
  }

  public static boolean isNot(String text, String patternStr) {
    return !is(text, patternStr);
  }


  /**
   * check text contains or not.
   *
   * @param text
   * @param regExpEnum
   * @return
   */
  public static boolean find(String text, RegExpEnum regExpEnum) {
    return find(text, regExpEnum.getExpression());
  }

  /**
   * 检查text中是否包含指定的正则
   *
   * @param text
   * @param patternStr
   * @return
   */
  public static boolean find(String text, String patternStr) {
    Pattern p = Pattern.compile(patternStr);
    Matcher m = p.matcher(text);
    return m.find();
  }


  /**
   * 判断是否是手机号
   *
   * @param mobile
   * @return
   */
  public static boolean isMobile(String mobile) {
    return is(mobile, RegExpEnum.MOBILE);
  }

  /**
   * 判断是否是图片
   *
   * @param fileName
   * @return
   */
  public static boolean isImg(String fileName) {
    return is(fileName, RegExpEnum.PICTURE.getExpression());
  }

  /**
   * 判断是否是车牌号
   *
   * @param carNumber
   * @return
   */
  public static boolean isCarNumber(String carNumber) {
    return is(carNumber, RegExpEnum.CAR_NUMBER.getExpression());
  }

  /**
   * 判断是否是合法的用户名
   *
   * @param userName
   * @return
   */
  public static boolean isLegalUserName(String userName) {
    return is(userName, RegExpEnum.USER_NAME);
  }

  /**
   * 判断是否是合法的密码
   *
   * @param password
   * @return
   */
  public static boolean isLegalPassword(String password) {
    return is(password, RegExpEnum.PASSWORD);
  }

  /**
   * 功能：检查是否为URL。<br/> 包括Http，Ftp,News,Nntpurl,Telnet,Gopher,Wais,Mailto,File, Prosperurl和Otherurl。
   *
   * @param url 网址，不仅仅http或者https。
   * @return 如果是返回true，不是返回false。
   */
  public static boolean isUrl(String url) {
    String regex = "^((https|http|ftp|rtsp|mms)?://)"
      + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
      + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
      + "|" // 允许IP和DOMAIN（域名）
      + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
      + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
      + "[a-z]{2,6})" // first level domain- .com or .museum
      + "(:[0-9]{1,4})?" // 端口- :80
      + "((/?)|" // a slash isn't required if there is no file name
      + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";

    return is(url, regex);
  }

  /**
   * 判断是否为IP V4地址。<br/>
   *
   * @param text 内容
   * @return 如果是返回true，不是返回false
   */
  public static boolean isIPV4(String text) {
    Pattern pattern = Pattern
      .compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");
    return pattern.matcher(text).matches();
  }

  /**
   * 功能：检查是否为邮箱地址。
   *
   * @param email 邮箱地址
   * @return 如果是返回true，不是返回false。
   */
  public static boolean isEmail(String email) {
    Pattern pattern = Pattern
      .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    return pattern.matcher(email).matches();
  }

  /**
   * 判断字符串是不是纯数字([0-9]+)。
   *
   * @param str 字符串。
   * @return 如果是返回true，不是返回false
   */
  public static boolean isNumeric(String str) {
    Pattern pattern = Pattern.compile("[0-9]+");
    return pattern.matcher(str).matches();
  }

  /**
   * 判断字符串只含有字母和数字中一种或两种。
   *
   * @param str 字符串。
   * @return 如果是返回true，不是返回false。
   */
  public static boolean isNumericAndEnglish(String str) {
    byte[] bytes = str.getBytes();
    for (byte temp : bytes) {
      if (temp < 48 || (temp > 57 && temp < 65)
        || (temp > 90 && temp < 97) || temp > 122) {
        return false;
      }
    }
    return true;
  }


  /**
   * 取括号中的值 abc(type)--> type
   *
   * @param managers
   * @return
   */
  public static List<String> getByBracket(String managers) {
    List<String> ls = new ArrayList<>();
    Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
    Matcher matcher = pattern.matcher(managers);
    while (matcher.find()) {
      ls.add(matcher.group());
    }
    return ls;
  }

  /**
   * get value in bracket
   *
   * <pre>
   * "a(abcd)", "a" --> abcd
   * </pre>
   *
   * @param str
   * @param prefix
   * @return
   */
  public static String getByBracket(String str, String prefix) {
    if (str == null) {
      log.warn("str is null");
      return str;
    }
    if (prefix == null) {
      log.warn("prefix is null");
      return str;
    }
    return str.trim().replaceAll("^" + prefix + "\\((.+)\\)$", "$1").trim();
  }


  /**
   * replace multi space with one space.
   *
   * @param str
   * @return
   */
  public static String trimSpace(String str) {
    if (StringUtil.isEmpty(str)) {
      return StringUtil.EMPTY;
    }
    return str.replaceAll("[\\s]+", " ");
  }

  /**
   * remove chinese
   *
   * @param value
   * @return
   */
  public static String removeChinese(String value) {
    if (Objects.isNull(value)) {
      value = "";
    }
    Pattern pattern = Pattern.compile(RegExpEnum.CHINESE.getExpression());
    Matcher matcher = pattern.matcher(value);
    return matcher.replaceAll("");
  }

}
