package com.github.seaxlab.core.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.TreeSet;

/**
 * number util
 *
 * @author spy
 * @version 1.0 2019-08-14
 * @since 1.0
 */
@Slf4j
public final class NumberUtil {

    private static final String ZERO = "0";

    private NumberUtil() {
    }

    /**
     * 判断是否为0
     *
     * @param amount
     * @return
     */
    public static boolean isZero(BigDecimal amount) {
        if (null == amount || BigDecimal.ZERO.compareTo(amount) == 0) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否为0
     *
     * @param value
     * @return
     */
    public static boolean isZero(Long value) {
        if (value == null || value.longValue() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否正数
     *
     * @param value
     * @return
     */
    public static boolean isPositive(Long value) {
        if (value == null || value.longValue() <= 0) {
            return false;
        }
        return true;
    }


    public static boolean isPositive(Integer value) {
        if (value == null || value.intValue() <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是负数
     *
     * @param value
     * @return
     */
    public static boolean isNegative(Long value) {
        if (value == null || value.longValue() >= 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是负数
     *
     * @param value
     * @return
     */
    public static boolean isNegative(Integer value) {
        if (value == null || value.intValue() >= 0) {
            return false;
        }
        return true;
    }


    /**
     * integer to BigDecimal
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(Integer value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value);
    }

    /**
     * Long 转 BigDecimal
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(Long value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value);
    }

    /**
     * double to BigDecimal
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(Double value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value);
    }


    /**
     * BigDecimal 转long
     *
     * @param value
     * @return
     */
    public static Long toLong(BigDecimal value) {
        if (value == null) {
            return 0L;
        }
        return value.longValue();
    }

    /**
     * 转换成字符串
     *
     * @param value
     * @return
     */
    public static String toStr(Long value) {
        if (value == null) {
            return "0";
        }
        return value.toString();
    }

    /**
     * int数值字符串累加
     *
     * @param values values
     * @return long
     */
    public static int addInt(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return 0;
        }
        int sum = 0;
        for (String value : values) {
            if (StringUtil.isBlank(value)) {
                log.warn("value is blank.");
                continue;
            }
            sum += Integer.parseInt(value);
        }

        return sum;
    }

    /**
     * long数值字符串累加
     *
     * @param values values
     * @return long
     */
    public static long addLong(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return 0L;
        }
        long sum = 0;
        for (String value : values) {
            if (StringUtil.isBlank(value)) {
                log.warn("value is blank.");
                continue;
            }
            sum += Long.parseLong(value);
        }

        return sum;
    }

    /**
     * 多个数值累加（高精度）
     *
     * @param values
     * @return
     */
    public static BigDecimal addStr(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        String value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value);
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.add(new BigDecimal(value));
            }
        }
        return result;
    }


    /**
     * 多个数值累加（高精度）
     *
     * @param values
     * @return
     */
    public static BigDecimal add(Number... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        Number value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value.toString());
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.add(new BigDecimal(value.toString()));
            }
        }
        return result;
    }

    /**
     * 多个数做减法
     *
     * @param values
     * @return
     */
    public static BigDecimal substract(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        String value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value);
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.subtract(new BigDecimal(value));
            }
        }
        return result;
    }

    /**
     * 多个数减法
     *
     * @param values
     * @return
     */
    public static BigDecimal substract(Number... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        Number value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value.toString());
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.subtract(new BigDecimal(value.toString()));
            }
        }
        return result;
    }

    /**
     * 在连续的数值范围中取[中位数]
     * 如果有两个中位数取【后者】
     * 例如1~9-->5
     * 例如1~10-->6 ()
     *
     * @param begin
     * @param end
     * @return
     */
    public static int median(final int begin, final int end) {

        int delta = end - begin;

        return ((int) Math.ceil(delta / 2.0)) + begin;
    }

    /**
     * 在连续的数值范围中取[中位数]
     * 如果有两个中位数取【前者】
     * 例如1~9-->5
     * 例如1~10-->6 ()
     *
     * @param begin
     * @param end
     * @return
     */
    public static int medianBefore(final int begin, final int end) {

        int delta = end - begin;

        return ((int) Math.floor(delta / 2.0)) + begin;
    }

    /**
     * 中位数
     *
     * @param items
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> T median(final T... items) {
        Validate.notEmpty(items);
        Validate.noNullElements(items);
        final TreeSet<T> sort = new TreeSet<>();
        Collections.addAll(sort, items);
        @SuppressWarnings("unchecked") //we know all items added were T instances
        final T result = (T) sort.toArray()[(sort.size() - 1) / 2];
        return result;
    }

    public static long min(final byte... array) {
        return NumberUtils.min(array);
    }

    public static long max(final byte... array) {
        return NumberUtils.max(array);
    }

    public static long min(final int... array) {
        return NumberUtils.min(array);
    }

    public static long max(final int... array) {
        return NumberUtils.max(array);
    }

    public static long min(final long... array) {
        return NumberUtils.min(array);
    }

    public static long max(final long... array) {
        return NumberUtils.max(array);
    }


    /**
     * 除法，保留两位小数
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal divide(BigDecimal num1, BigDecimal num2) {
        return divide(num1, num2, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(Integer num1, Integer num2, int scale, RoundingMode roundingMode) {
        return divide(toBigDecimal(num1), toBigDecimal(num2), scale, roundingMode);
    }

    public static BigDecimal divide(Long num1, Long num2, int scale, RoundingMode roundingMode) {
        return divide(toBigDecimal(num1), toBigDecimal(num2), scale, roundingMode);
    }

    public static BigDecimal divide(Double num1, Double num2, int scale, RoundingMode roundingMode) {
        return divide(toBigDecimal(num1), toBigDecimal(num2), scale, roundingMode);
    }

    /**
     * 除法
     *
     * @param num1
     * @param num2
     * @param scale
     * @param roundingMode
     * @return
     */
    public static BigDecimal divide(BigDecimal num1, BigDecimal num2, int scale, RoundingMode roundingMode) {
        Preconditions.checkNotNull(num1, "num1 cannot be null");
        Preconditions.checkNotNull(num2, "num2 cannot be null");

        if (isZero(num2)) {
            log.error("num2 is zero! plz check");
            return BigDecimal.ZERO;
        }

        return num1.divide(num2, scale, roundingMode);
    }

    /**
     * 分转元
     *
     * @param num
     * @return
     */
    public static String centToYuan(Long num) {
        DecimalFormat twoDecimal = new DecimalFormat("0.00");
        Double yuan = num / 100.0;
        return twoDecimal.format(yuan);
    }

    /**
     * scale
     * 有效小数位后向上
     * <pre>
     * 1.114-> 1.12
     * 1.115-> 1.12
     * </pre>
     *
     * @param num
     * @param scale
     * @return
     */
    public static BigDecimal scaleUp(double num, int scale) {
        return scale(num, scale, RoundingMode.UP);
    }

    /**
     * scale up
     * double != num
     *
     * @param num
     * @param scale
     * @return
     */
    public static BigDecimal scaleUp(String num, int scale) {
        return scale(num, scale, RoundingMode.UP);
    }

    /**
     * scale down
     * 有效小数位后向下
     * <pre>
     *     1.114-->1.11
     *     1.115-->1.11
     * </pre>
     *
     * @param num
     * @param scale
     * @return
     */
    public static BigDecimal scaleDown(double num, int scale) {
        return scale(num, scale, RoundingMode.DOWN);
    }

    /**
     * scale down
     * double != string
     *
     * @param num
     * @param scale
     * @return
     */
    public static BigDecimal scaleDown(String num, int scale) {
        return scale(num, scale, RoundingMode.DOWN);
    }

    /**
     * scale
     *
     * @param num
     * @param scale
     * @param mode
     * @return
     */
    public static BigDecimal scale(double num, int scale, RoundingMode mode) {
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, mode);
    }

    /**
     * scale
     * double != str
     *
     * @param num
     * @param scale
     * @param mode
     * @return
     */
    public static BigDecimal scale(String num, int scale, RoundingMode mode) {
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, mode);
    }


    // ---------N进制转换

    /**
     * 十进制转成二进制
     */
    public static String convert10To2(int val) {
        return convertInt(val, 1, 1);
    }

    /**
     * 二进制转十进制
     *
     * @param val
     * @return
     */
    public static Long convert2To10(String val) {
        return Long.parseLong(val, 2);
    }

    /**
     * 十进制转化成八进制
     *
     * @param value
     * @return
     */
    public static String convert10To8(int value) {
        return convertInt(value, 7, 3);
    }

    /**
     * 八进制转十进制
     *
     * @param val
     * @return
     */
    public static Long convert8To10(String val) {
        return Long.parseLong(val, 8);
    }

    /**
     * 十进制转换成十六进制
     *
     * @param val
     * @return
     */
    public static String convert10To16(int val) {
        return convertInt(val, 15, 4);
    }

    /**
     * 十六进制转十进制
     *
     * @param val
     * @return
     */
    public static Long convert16To10(String val) {
        return Long.parseLong(val, 16);
    }

    /**
     * 把int类型数据转换成其他进制
     *
     * @param num    转换值
     * @param base   与基数
     * @param offset 位移数
     * @return
     */
    private static String convertInt(int num, int base, int offset) {
        if (num == 0) {
            return ZERO;
        }
        char[] chs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] result = new char[32];
        int index = result.length;
        while (num != 0) {
            int temp = num & base;
            result[--index] = chs[temp];
            num = num >>> offset;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = index; i < result.length; i++) {
            sb.append(result[i]);
        }
        return sb.toString();
    }

    /**
     * 初始化 62 进制数据，索引位置代表转换字符的数值 0-61，比如 A代表10，z代表61
     */
//    private static String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 匹配字符串只包含数字和大小写字母
     */
    private static String REGEX_NORMAL_62 = "^[0-9a-zA-Z]+$";

    /**
     * 进制转换比率62
     */
    private static int SCALE_62 = 62;

    /**
     * 十进制数字转为62进制字符串
     *
     * @param val 十进制数字
     * @return 62进制字符串
     */
    public static String convert10To62(long val) {
        if (val < 0) {
            throw new IllegalArgumentException("this is an Invalid parameter:" + val);
        }
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (Math.abs(val) > SCALE_62 - 1) {
            //从最后一位开始进制转换，取转换后的值，最后反转字符串
            remainder = Long.valueOf(val % SCALE_62).intValue();
            sb.append(CHARS.charAt(remainder));
            val = val / SCALE_62;
        }
        //获取最高位
        sb.append(CHARS.charAt(Long.valueOf(val).intValue()));
        return sb.reverse().toString();
    }

    /**
     * 十进制数字转为62进制字符串
     *
     * @param val 62进制字符串
     * @return 十进制数字
     */
    public static long convert62To10(String val) {
        if (val == null) {
            throw new NumberFormatException("null");
        }
        if (!val.matches(REGEX_NORMAL_62)) {
            throw new IllegalArgumentException("this is an Invalid parameter:" + val);
        }
        String tmp = val.replace("^0*", "");

        long result = 0;
        int index = 0;
        int length = tmp.length();
        for (int i = 0; i < length; i++) {
            index = CHARS.indexOf(tmp.charAt(i));
            result += (long) (index * Math.pow(SCALE_62, length - i - 1));
        }
        return result;
    }

    /**
     * 获取数字的长度
     * 1100 -> 4
     *
     * @param value 数字值
     * @return int
     */
    public static int length(int value) {
        return String.valueOf(value).length();
    }


    /**
     * 百分比
     *
     * @param part
     * @param total
     * @return x.xxx
     */
    public static BigDecimal ratio(long part, long total) {
        return divide(part, total, 2, RoundingMode.DOWN);
    }

    /**
     * 百分比
     *
     * @param part
     * @param total
     * @param scale
     * @return x.xx
     */
    public static BigDecimal ratio(long part, long total, int scale) {
        return divide(part, total, scale, RoundingMode.DOWN);
    }

    /**
     * 百分比, toString or doubleValue()
     *
     * @param part
     * @param total
     * @param scale
     * @param roundingMode
     * @return x.xxx
     */
    public static BigDecimal ratio(long part, long total, int scale, RoundingMode roundingMode) {
        if (scale < 0) {
            scale = 2;
        }
        if (roundingMode == null) {
            roundingMode = RoundingMode.DOWN;
        }
        return divide(part, total, scale, roundingMode);
    }

    /**
     * 同环比
     *
     * @param before
     * @param now
     * @return x.xx
     */
    public static BigDecimal comparisonRatio(long before, long now) {
        return comparisonRatio(before, now, 2, RoundingMode.DOWN);
    }

    /**
     * 同环比
     *
     * @param before
     * @param now
     * @param scale
     * @return
     */
    public static BigDecimal comparisonRatio(long before, long now, int scale) {
        return comparisonRatio(before, now, scale, RoundingMode.DOWN);
    }

    /**
     * 同环比, toString or doubleValue()
     *
     * @param before
     * @param now
     * @param scale
     * @param roundingMode
     * @return x.xx
     */
    public static BigDecimal comparisonRatio(long before, long now, int scale, RoundingMode roundingMode) {
        if (scale < 0) {
            scale = 2;
        }
        if (roundingMode == null) {
            roundingMode = RoundingMode.DOWN;
        }
        return divide(now - before, before, scale, roundingMode);
    }
}
