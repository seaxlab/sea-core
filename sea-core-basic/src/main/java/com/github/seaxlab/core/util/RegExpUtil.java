package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * 匹配非负整数（正整数 + 0)
     */
    public static final String non_negative_integers_regexp = "^\\d+$";

    /**
     * 匹配不包括零的非负整数（正整数 > 0)
     */
    public static final String non_zero_negative_integers_regexp = "^[1-9]+\\d*$";

    /**
     * 匹配非正整数（负整数 + 0）
     */
    public static final String non_positive_integers_regexp = "^((-\\d+)|(0+))$";

    /**
     * 匹配负整数
     */
    public static final String negative_integers_regexp = "^-[0-9]*[1-9][0-9]*$";

    /**
     * 匹配整数
     */
    public static final String integer_regexp = "^-?\\d+$";

    /**
     * 匹配非负浮点数（正浮点数 + 0）
     */
    public static final String non_negative_rational_numbers_regexp = "^\\d+(\\.\\d+)?$";

    /**
     * 匹配正浮点数
     */
    public static final String positive_rational_numbers_regexp = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";

    /**
     * 匹配非正浮点数（负浮点数 + 0）
     */
    public static final String non_positive_rational_numbers_regexp = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";

    /**
     * 匹配负浮点数
     */
    public static final String negative_rational_numbers_regexp = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

    /**
     * 匹配浮点数
     */
    public static final String rational_numbers_regexp = "^(-?\\d+)(\\.\\d+)?$";

    /**
     * 匹配由26个英文字母组成的字符串
     */
    public static final String letter_regexp = "^[A-Za-z]+$";

    /**
     * 匹配由26个英文字母的大写组成的字符串
     */
    public static final String upward_letter_regexp = "^[A-Z]+$";

    /**
     * 匹配由26个英文字母的小写组成的字符串
     */
    public static final String lower_letter_regexp = "^[a-z]+$";

    /**
     * 匹配由数字和26个英文字母组成的字符串
     */
    public static final String letter_number_regexp = "^[A-Za-z0-9]+$";

    /**
     * 匹配由数字、26个英文字母或者下划线组成的字符串
     */
    public static final String letter_number_underline_regexp = "^\\w+$";

    /**
     * 数字、字母、中划线、下划线、点号
     */
    public static final String normal_id_regexp = "^[A-Za-z0-9\\-\\_\\.]+$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(16[0-9])|(17[0-9])|(19[0-9])|(18[0-9]))\\d{8}$";


    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z0-9]{8,16}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}]{6,16}$";

    /**
     * 正则表达式:验证日期
     */
    public static final String REGEX_DATE = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";

    /**
     * 正则表达式:车牌号
     */
    public static final String REGEX_CAR_NUMBER = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";

    /**
     * 正则表达式:图片
     */
    public static final String REGEX_PICTURE = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";

    /**
     * 中文(常规)
     */
    public static final String REGEX_CHINESE = "[\u4e00-\u9fa5]";

    /**
     * 中文+中文符号
     */
    public static final String REGEX_CHINESE2 = "[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]";

    /**
     * 汉字扩充（繁体字、不常见字）
     */
    public static final String REGEX_CHINESE_COMPLEX = "[\u3400-\u4db5]";

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
        return is(mobile, REGEX_MOBILE);
    }

    /**
     * 判断是否是图片
     *
     * @param fileName
     * @return
     */
    public static boolean isImg(String fileName) {
        return is(fileName, REGEX_PICTURE);
    }

    /**
     * 判断是否是车牌号
     *
     * @param carNumber
     * @return
     */
    public static boolean isCarNumber(String carNumber) {
        return is(carNumber, REGEX_CAR_NUMBER);
    }

    /**
     * 判断是否是合法的用户名
     *
     * @param userName
     * @return
     */
    public static boolean isLegalUserName(String userName) {
        return is(userName, REGEX_USERNAME);
    }

    /**
     * 判断是否是合法的密码
     *
     * @param password
     * @return
     */
    public static boolean isLegalPassword(String password) {
        return is(password, REGEX_PASSWORD);
    }

    /**
     * 功能：检查是否为URL。<br/>
     * 包括Http，Ftp,News,Nntpurl,Telnet,Gopher,Wais,Mailto,File,
     * Prosperurl和Otherurl。
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
     * 取括号中的值
     * abc(type)--> type
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


}
