package com.github.spy.sea.core.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.TreeSet;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-14
 * @since 1.0
 */
@Slf4j
public final class NumberUtil {

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
     * 多个数值累加
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


}
