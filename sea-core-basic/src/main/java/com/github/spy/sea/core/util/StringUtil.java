package com.github.spy.sea.core.util;

import com.github.spy.sea.core.common.CharConst;
import com.github.spy.sea.core.common.SymbolConst;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 工具
 *
 * @author spy
 */
@Slf4j
public final class StringUtil {

    /**
     * empty str
     */
    public static final String EMPTY = "";

    /**
     * space
     */
    public static final String SPACE = " ";

    private StringUtil() {
    }

    /**
     * 字符串是否为空
     *
     * @param params
     * @return
     */
    public static boolean isAllEmpty(Object... params) {

        for (Object param : params) {
            if (param != null && param.toString().length() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串是否为空
     *
     * @param params
     * @return
     */
    public static boolean isEmpty(Object... params) {

        for (Object param : params) {
            if (param == null || "".equals(param)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串是否不为空
     *
     * @param params
     * @return
     */
    public static boolean isNotEmpty(Object... params) {

        for (Object param : params) {
            if (param == null || "".equals(param)) {
                return false;
            }
        }
        return true;
    }

    /**
     * join the string by separator.
     *
     * @param separator
     * @param strs
     * @return
     */
    public static String join(String separator, String... strs) {
        Preconditions.checkState(isNotEmpty(separator), "separator cannot be null");
        if (strs == null || strs.length < 2) {
            log.warn("The length of strs should be more than 2.");
            return "";
        }
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            if (isNotEmpty(str)) {
                if (strBuilder.toString().length() != 0) {
                    strBuilder.append(separator);
                }
                strBuilder.append(str);
            }
        }

        return strBuilder.toString();
    }

    /**
     * 字符串左侧补零
     *
     * @param obj
     * @param strLength
     * @return
     */
    public static String addZeroLeft(Object obj, int strLength) {
        String str = obj == null ? "" : obj.toString();
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuilder sb = new StringBuilder();
                // 左补0
                sb.append("0").append(str);
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 将set 集合转成 String
     *
     * @param set
     * @return
     */
    public static String setToString(Set set) {
        if (null == set || set.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object object : set) {
            if (object instanceof String || object instanceof Long || object instanceof Integer) {
                if (isNotEmpty(object.toString())) {
                    sb.append(object.toString()).append(",");
                }
            }
        }
        if (sb.length() == 0) {
            return "";
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 判断是否相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean eq(String str1, String str2) {
        if (Objects.nonNull(str1) && Objects.nonNull(str2)) {
            return str1.equals(str2);
        }
        return false;
    }

    /**
     * 不能全是空格
     *
     * @param str
     * @param defaultStr
     * @param <T>
     * @return
     */
    public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
        return StringUtils.isBlank(str) ? defaultStr : str;
    }

    /**
     * 只要有内容就为true
     *
     * @param str
     * @param defaultStr
     * @param <T>
     * @return
     */
    public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr) {
        return StringUtils.isEmpty(str) ? defaultStr : str;
    }

    public static String defaultString(final String str, final String defaultStr) {
        return str == null ? defaultStr : str;
    }

    /**
     * 设置返回有值的参数
     *
     * @param str1
     * @param args
     * @return
     */
    public static String defaultIfBlank(String str1, String... args) {
        Preconditions.checkNotNull(args, "参数不能为空");
        if (isEmpty(str1)) {
            for (int i = 0; i < args.length; i++) {
                String item = args[i];
                if (!isEmpty(item)) {
                    return item;
                }
            }
        } else {
            return str1;
        }

        return null;
    }

    /**
     * @param originalName
     * @return
     */
//    public static String toCamelCase(String originalName) {
//        String[] words = originalName.split("-+");
//        StringBuilder nameBuilder = new StringBuilder(originalName.length());
//        for (String word : words) {
//            if (nameBuilder.length() == 0) {
//                nameBuilder.append(word);
//            } else {
//                nameBuilder.append(word.substring(0, 1).toUpperCase());
//                nameBuilder.append(word.substring(1));
//            }
//        }
//        return nameBuilder.toString();
//    }

    /**
     * 将驼峰式命名的字符串转换为下划线方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     * 例如：
     *
     * <pre>
     * HelloWorld=》hello_world
     * Hello_World=》hello_world
     * HelloWorld_test=》hello_world_test
     * </pre>
     *
     * @param str 转换前的驼峰式命名的字符串，也可以为下划线形式
     * @return 转换后下划线方式命名的字符串
     */
    public static String toUnderlineCase(CharSequence str) {
        return toSymbolCase(str, CharConst.UNDERLINE);
    }

    /**
     * 将驼峰式命名的字符串转换为使用符号连接方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     *
     * @param str    转换前的驼峰式命名的字符串，也可以为符号连接形式
     * @param symbol 连接符
     * @return 转换后符号连接方式命名的字符串
     */
    public static String toSymbolCase(CharSequence str, char symbol) {
        if (str == null) {
            return null;
        }

        final int length = str.length();
        final StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < length; i++) {
            c = str.charAt(i);
            final Character preChar = (i > 0) ? str.charAt(i - 1) : null;
            if (Character.isUpperCase(c)) {
                // 遇到大写字母处理
                final Character nextChar = (i < str.length() - 1) ? str.charAt(i + 1) : null;
                if (null != preChar && Character.isUpperCase(preChar)) {
                    // 前一个字符为大写，则按照一个词对待
                    sb.append(c);
                } else if (null != nextChar && Character.isUpperCase(nextChar)) {
                    // 后一个为大写字母，按照一个词对待
                    if (null != preChar && symbol != preChar) {
                        // 前一个是非大写时按照新词对待，加连接符
                        sb.append(symbol);
                    }
                    sb.append(c);
                } else {
                    // 前后都为非大写按照新词对待
                    if (null != preChar && symbol != preChar) {
                        // 前一个非连接符，补充连接符
                        sb.append(symbol);
                    }
                    sb.append(Character.toLowerCase(c));
                }
            } else {
                if (sb.length() > 0 && Character.isUpperCase(sb.charAt(sb.length() - 1)) && symbol != c) {
                    // 当结果中前一个字母为大写，当前为小写，说明此字符为新词开始（连接符也表示新词）
                    sb.append(symbol);
                }
                // 小写或符号
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。<br>
     * 例如：hello_world=》helloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String toCamelCase(CharSequence name) {
        if (null == name) {
            return null;
        }

        String name2 = name.toString();
        if (name2.contains(SymbolConst.UNDERLINE)) {
            final StringBuilder sb = new StringBuilder(name2.length());
            boolean upperCase = false;
            for (int i = 0; i < name2.length(); i++) {
                char c = name2.charAt(i);

                if (c == CharConst.UNDERLINE) {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
            return sb.toString();
        } else {
            return name2;
        }
    }

    /**
     * 生成唯一标识,默认分号隔开
     *
     * @param args
     * @return
     */
    public static String uniqueKey(Object... args) {
        return uniqueKey(":", args);
    }

    public static String uniqueKey(String separatorStr, Object... args) {
        if (args == null) {
            return null;
        }

        if (separatorStr == null || separatorStr.trim().length() == 0) {
            separatorStr = ":";
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
            Object item = args[i];
            if (i != 0) {
                builder.append(separatorStr);
            }
            builder.append(item);
        }

        return builder.toString();
    }

    /**
     * this is simple format string for all scene
     * <p>
     * StringUtil.format("hi, this is {}", "hello world");
     * </p>
     *
     * @param strPattern
     * @param args
     * @return
     */
    public static String format(String strPattern, Object... args) {
        return MessageFormatter.arrayFormat(strPattern, args).getMessage();
    }


    /**
     * get sub str length string
     *
     * @param str
     * @param len
     * @return
     */
    public static String left(String str, int len) {
        return StringUtils.left(str, len);
    }

    public static String left64(String str) {
        return left(str, 64);
    }

    public static String left128(String str) {
        return left(str, 128);
    }

    public static String left512(String str) {
        return left(str, 512);
    }

    public static String right(String str, int len) {
        return StringUtils.right(str, len);
    }

    public static String mid(String str, int pos, int len) {
        return StringUtils.mid(str, pos, len);
    }

    /**
     * split str
     * return value maybe null, plz note!!
     *
     * @param str
     * @return
     */
    public static String[] split(String str) {
        return StringUtils.split(str);
    }

    public static String[] split(String str, char separatorChar) {
        return StringUtils.split(str, separatorChar);
    }

    /**
     * split to Iterable
     * return value not null
     *
     * @param str
     * @param separatorChar
     * @return
     */
    public static Iterable<String> splitToIterable(String str, char separatorChar) {
        if (StringUtils.isEmpty(str)) {
            return ListUtil.empty();
        }
        return Splitter.on(separatorChar).omitEmptyStrings().split(str);
    }

    /**
     * split to List
     * return value not null
     *
     * @param str
     * @param separatorChar
     * @return
     */
    public static List<String> splitToList(String str, char separatorChar) {
        if (StringUtils.isEmpty(str)) {
            return ListUtil.empty();
        }
        return Splitter.on(separatorChar).omitEmptyStrings().splitToList(str);
    }


    /**
     * 截取字符串
     *
     * @param str
     * @param start
     * @return
     */
    public static String substring(String str, int start) {
        return StringUtils.substring(str, start);
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String substring(String str, int start, int end) {
        return StringUtils.substring(str, start, end);
    }


    /**
     * 去掉指定字符串的开头的指定字符
     *
     * @param stream 原始字符串
     * @param trim   要删除的字符串
     * @return
     */
    public static String replaceStart(String stream, String trim) {

        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trim == null || trim.length() == 0) {
            return stream;
        }
        // 要删除的字符串结束位置
        int end;
        // 正规表达式
        String regPattern = "[" + trim + "]*+";
        Pattern pattern = Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
        // 去掉原始字符串开头位置的指定字符
        Matcher matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            end = matcher.end();
            stream = stream.substring(end);
        }
        // 返回处理后的字符串
        return stream;
    }

    /**
     * 从指定位置开始匹配字符
     *
     * @param str       字符串
     * @param index     索引位置
     * @param substring 子字符串位置
     * @return true/false
     */
    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        if (index + substring.length() > str.length()) {
            return false;
        }
        for (int i = 0; i < substring.length(); i++) {
            if (str.charAt(index + i) != substring.charAt(i)) {
                return false;
            }
        }
        return true;
    }

}
