package com.github.seaxlab.core.enums;

import lombok.Getter;

/**
 * Common RegExpression enum
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Getter
public enum RegExpEnum implements IBaseRegExpEnum<String> {
  //
  ID("^[A-Za-z0-9\\-\\_\\.]+$", "字母+数字+中划线+下划线+点号"),
  CODE("^[A-Za-z0-9\\-\\_]+", "字母+数字+中划线+下划线"),
  NAME("^[\u4e00-\u9fa5A-Za-z0-9\\-\\_]+", "中文+字母+数字+中划线+下划线"),
  NAME_SIMPLE("^[\u4e00-\u9fa5A-Za-z0-9]+", "中文+字母+数字"), //
  NAME_SIMPLE2("^[\u4e00-\u9fa50-9]+", "中文+数字"), //
  USER_NAME("^[a-zA-Z0-9]{8,16}$", "字母+数字 8-16位"), //
  PASSWORD("^[a-zA-Z0-9~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}]{6,16}$", "字母+数字+特殊符号6-16位"), //
  MOBILE("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(16[0-9])|(17[0-9])|(19[0-9])|(18[0-9]))\\d{8}$", "手机号"),//
  EMAIL("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", "电子邮件"), //
  DATE("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$", "日期"),//
  //
  CHINESE("[\u4e00-\u9fa5]", "中文"),//(常规)
  CHINESE_AND_SYMBOL("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]", "中文+中文符号"),//
  CHINESE_AND_EXTEND("[\u3400-\u4db5]", "汉字扩充（繁体字、不常见字）"),//
  //
  // for biz case
  CAR_NUMBER("([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})", "车牌"), //
  PICTURE(".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$", "图片后缀"), //


  //
  LETTER("^[A-Za-z]+$", "大小写字母"), //
  LETTER_LOWER("^[a-z]+$", "小写字母"), //
  LETTER_UPPER("^[A-Z]+$", "大写字母"), //
  LETTER_AND_NUMBER("^[A-Za-z0-9]+$", "字母+数字"), //
  LETTER_AND_NUMBER_AND_UNDER_LINE("^\\w+$", "数字、26个英文字母或者下划线"), //
  LETTER_AND_NUMBER_AND_MIDDLE_UNDER_DOT("[A-Za-z0-9\\-\\_\\.]+$", "字母+数字+中划线+下划线+点号"), //

  //
  INTEGER("^-?\\d+$", "整数"), //
  INTEGER_POSITIVE("^[1-9]+\\d*$", "正整数"), //
  INTEGER_POSITIVE_AND_ZERO("^\\d+$", "正整数+0"), //
  INTEGER_NEGATIVE("^-[0-9]*[1-9][0-9]*$", "负整数"), //
  INTEGER_NEGATIVE_AND_ZERO("^((-\\d+)|(0+))$", "负整数+0"), //

  FLOAT("^(-?\\d+)(\\.\\d+)?$", "浮点数"),
  FLOAT_POSITIVE("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$", "正浮点数"),//
  FLOAT_POSITIVE_AND_ZERO("^\\d+(\\.\\d+)?$", "非负浮点数（正浮点数 + 0）"),//
  FLOAT_NEGATIVE("^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$", "负浮点数"),
  FLOAT_NEGATIVE_AND_ZERO("^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$", "非正浮点数（负浮点数 + 0）"),
  //
  EMOJI("[\ud83c\udf00-\ud83d\ude4f]|[\ud83d\ude80-\ud83d\udeff]", "EMOJI表情符号"),
  //经纬度
  //（整数部分为0～180，必须输入1到8位小数）
  LONGITUDE("^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,8}|1[0-7]?\\d{1}\\.\\d{1,8}|180\\.0{1,8})$", "经度：-180.0～+180.0"),
  //（整数部分为0～90，必须输入1到8位小数）
  LATITUDE("^[\\-\\+]?([0-8]?\\d{1}\\.\\d{1,8}|90\\.0{1,8})$", "纬度：-90.0～+90.0"),

  //
  NONE("", "none");

  private final String expression;
  private final String desc;

  RegExpEnum(String expression, String desc) {
    this.expression = expression;
    this.desc = desc;
  }

}
