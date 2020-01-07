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
     * 保留两位小数
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal divide(BigDecimal num1, BigDecimal num2) {
        return divide(num1, num2, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(BigDecimal num1, BigDecimal num2, int scale, RoundingMode roundingMode) {
        Preconditions.checkNotNull(num1, "");
        Preconditions.checkNotNull(num2, "");
        return num1.divide(num1, scale, roundingMode);
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
        return String.valueOf(twoDecimal.format(yuan));
    }

}
