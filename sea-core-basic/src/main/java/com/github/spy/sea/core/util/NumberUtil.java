package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

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
}
