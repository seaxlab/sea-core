package com.github.seaxlab.core.util;

import com.github.seaxlab.core.common.CharConst;
import com.github.seaxlab.core.common.SymbolConst;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * String Util
 *
 * @author spy
 * @version 1.0
 * @since 1.0
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

    public static final String STR_NULL = "null";

    private static final String[] EMPTY_STRING_ARRAY = {};

    private StringUtil() {
    }

    /**
     * just return empty string
     *
     * @return
     */
    public static String empty() {
        return EMPTY;
    }

    /**
     * check is 'null', 不区分大小写
     *
     * @param value str
     * @return boolean
     */
    public static boolean isNullStr(String value) {
        if (value == null) {
            return false;
        }

        return value.trim().equalsIgnoreCase(STR_NULL);
    }

    /**
     * 字符串是否为空
     *
     * @param params objs
     * @return boolean
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
     * 判断对象全不为空
     *
     * @param params objs
     * @return boolean
     */
    public static boolean isAllNotEmpty(Object... params) {
        for (Object param : params) {
            if (param == null || param.toString().length() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串是否为空
     *
     * @param params objs
     * @return boolean
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
     * 空的标准：至少含有一个非空字符
     *
     * @param strs
     * @return
     */
    public static boolean isBlank(String... strs) {
        for (String str : strs) {
            if (str == null || str.trim().isEmpty()) { // 这里不需要containsText判断，本质都是一样的
                return true;
            }
        }
        return false;
    }

    /**
     * 必须全部不为空
     *
     * @param strs strs
     * @return boolean
     */
    public static boolean isAllBlank(String... strs) {
        for (String str : strs) {
            if (isNotBlank(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * is not blank.
     *
     * @param strs string array param
     * @return boolean
     */
    public static boolean isNotBlank(String... strs) {
        for (String str : strs) {
            if (str == null || str.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串是否不为空
     *
     * @param params str
     * @return boolean
     */
    public static boolean isNotEmpty(Object... params) {

        for (Object param : params) {
            if (param == null || "".equals(param)) {
                return false;
            }
        }
        return true;
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * join the string by separator.
     *
     * @param separator str
     * @param strs      str
     * @return boolean
     */
    public static String join(String separator, String... strs) {
        Preconditions.checkState(isNotEmpty(separator), "separator cannot be null");
        if (strs == null || strs.length == 0) {
            return "";
        }
        StringBuilder strBuilder = new StringBuilder();
        for (String str : strs) {
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
     * @param obj         obj
     * @param totalLength new str total length
     * @return string
     */
    public static String addZeroLeft(Object obj, int totalLength) {
        String str = obj == null ? "" : obj.toString();
        int strLen = str.length();
        if (strLen < totalLength) {
            StringBuilder sb = new StringBuilder();
            while (strLen < totalLength) {
                sb.append("0");
                strLen++;
            }
            sb.append(str);
            return sb.toString();
        }

        return str;
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
     * 只要有内容就为true(可以为空格)
     *
     * @param str
     * @param defaultStr
     * @param <T>
     * @return
     */
    public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr) {
        return StringUtils.isEmpty(str) ? defaultStr : str;
    }

    /**
     * if value is null, then return default str.
     *
     * @param str
     * @param defaultStr
     * @return
     */
    public static String defaultString(final String str, final String defaultStr) {
        return str == null ? defaultStr : str;
    }

    /**
     * if value is null, then return default str.
     *
     * @param value
     * @param defaultStr
     * @return
     */
    public static String defaultIfNull(final Integer value, final String defaultStr) {
        if (value == null) {
            return defaultStr;
        }
        return value.toString();
    }

    /**
     * if value is null, then return default str.
     *
     * @param value
     * @param defaultStr
     * @return
     */
    public static String defaultIfNull(final Long value, final String defaultStr) {
        if (value == null) {
            return defaultStr;
        }
        return value.toString();
    }

    /**
     * if value is null, then return default str.
     *
     * @param value
     * @param defaultStr
     * @return
     */
    public static String defaultIfNull(final Double value, final String defaultStr) {
        if (value == null) {
            return defaultStr;
        }
        return value.toString();
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

    public static String left20(String str) {
        return left(str, 20);
    }

    public static String left32(String str) {
        return left(str, 32);
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

    public static String left1024(String str) {
        return left(str, 1024);
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
     * @return iterable
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
     * Tokenize the given {@code String} into a {@code String} array via a
     * {@link StringTokenizer}.
     * <p>The given {@code delimiters} string can consist of any number of
     * delimiter characters. Each of those characters can be used to separate
     * tokens. A delimiter is always a single character; for multi-character
     * delimiters, consider using delimitedListToStringArray.
     *
     * @param str               the {@code String} to tokenize (potentially {@code null} or empty)
     * @param delimiters        the delimiter characters, assembled as a {@code String}
     *                          (each of the characters is individually considered as a delimiter)
     * @param trimTokens        trim the tokens via {@link String#trim()}
     * @param ignoreEmptyTokens omit empty tokens from the result array
     *                          (only applies to tokens that are empty after trimming; StringTokenizer
     *                          will not consider subsequent delimiters as token in the first place).
     * @return an array of the tokens
     * @see java.util.StringTokenizer
     * @see String#trim()
     */
    public static String[] tokenizeToStringArray(@Nullable String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

        if (str == null) {
            return EMPTY_STRING_ARRAY;
        }

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }


    public static <T> String toString(Iterable<T> iterable) {
        return toString(iterable, null);
    }

    public static <T> String toString(Iterable<T> iterable, Function<T, String> func) {
        return toString(iterable, func, ",");
    }

    public static <T> String toString(Iterable<T> iterable, Function<T, String> func, String split) {
        if (iterable == null) {
            return "";
        }
        if (split == null) {
            split = ",";
        }
        StringBuilder builder = new StringBuilder();
        for (T obj : iterable) {
            builder.append(func != null ? func.apply(obj) : obj).append(split);
        }
        if (builder.length() > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public static <T> String toString(T[] arrays) {
        return toString(arrays, null);
    }

    public static <T> String toString(T[] arrays, Function<T, String> func) {
        return toString(arrays, func, ",");
    }

    /**
     * array to String
     *
     * @param arrays
     * @param func
     * @param split
     * @param <T>
     * @return
     */
    public static <T> String toString(T[] arrays, Function<T, String> func, String split) {
        if (arrays == null) {
            return "";
        }
        if (split == null) {
            split = ",";
        }
        StringBuilder builder = new StringBuilder();
        for (T obj : arrays) {
            builder.append(func != null ? func.apply(obj) : obj).append(split);
        }
        if (builder.length() > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    /**
     * Copy the given {@link Collection} into a {@code String} array.
     * <p>The {@code Collection} must contain {@code String} elements only.
     *
     * @param collection the {@code Collection} to copy
     *                   (potentially {@code null} or empty)
     * @return the resulting {@code String} array
     */
    public static String[] toStringArray(@Nullable Collection<String> collection) {
        return ((collection == null || collection.isEmpty()) ? EMPTY_STRING_ARRAY : collection.toArray(EMPTY_STRING_ARRAY));
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

    /**
     * remove emoji expression.
     *
     * @param str
     * @return
     */
    private static final String REG_EXP_RMOJI = "[\ud83c\udf00-\ud83d\ude4f]|[\ud83d\ude80-\ud83d\udeff]";

    /**
     * remove emoji expression
     *
     * @param str string
     * @return string
     */
    public static String removeEmoji(String str) {
        return str.replaceAll(REG_EXP_RMOJI, "");
    }

    /**
     * check str contains part or not.
     * <p>
     * contains("abcdef", "xyz") //false
     * contains("abcdef", null) //true //IMPORTANT
     * contains("abcdef", "") //true //IMPORTANT
     *
     * @param str  string
     * @param part string
     * @return true/false
     */
    public static boolean contains(String str, String part) {
        part = part == null ? "" : part;
        return str != null && str.contains(part);
    }

    /**
     * 大小写敏感
     *
     * @param str
     * @param prefixArray
     * @return
     */
    public static boolean startsWith(String str, String... prefixArray) {
        return startsWith(str, true, prefixArray);
    }

    /**
     * 判断是否以指定prefix开始
     *
     * @param str
     * @param caseSensitive
     * @param prefixArray
     * @return
     */
    public static boolean startsWith(String str, boolean caseSensitive, String... prefixArray) {
        return Stream.of(prefixArray).anyMatch(item -> caseSensitive ? str.startsWith(item) : str.toLowerCase().startsWith(item.toLowerCase()));
    }

    /**
     * （大小写敏感）
     *
     * @param str         str
     * @param suffixArray suffix array
     * @return boolean
     */
    public static boolean endsWith(String str, String... suffixArray) {
        return endsWith(str, true, suffixArray);
    }

    /**
     * 判断是否以指定的suffix结束
     *
     * @param str           str
     * @param caseSensitive case sentitive
     * @param suffixArray   suffixs
     * @return boolean
     */
    public static boolean endsWith(String str, boolean caseSensitive, String... suffixArray) {
        return Stream.of(suffixArray).anyMatch(item -> caseSensitive ? str.endsWith(item) : str.toLowerCase().endsWith(item.toLowerCase()));
    }

    /**
     * 在指定位置插入字符
     *
     * @param str       原始字符串
     * @param offset    偏移量
     * @param character 新字符
     * @return str
     */
    public static String insert(String str, int offset, Character character) {
        StringBuilder builder = new StringBuilder(str);
        builder.insert(offset, character);
        return builder.toString();
    }

    /**
     * 在指定位置插入字符串
     *
     * @param str    原始字符串
     * @param offset 偏移量
     * @param newStr 新字符
     * @return string
     */
    public static String insert(String str, int offset, String newStr) {
        StringBuilder builder = new StringBuilder(str);
        builder.insert(offset, newStr);
        return builder.toString();
    }
}
