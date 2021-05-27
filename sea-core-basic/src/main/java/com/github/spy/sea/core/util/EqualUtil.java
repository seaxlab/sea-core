package com.github.spy.sea.core.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * equal util
 *
 * @author spy
 * @version 1.0 2019-05-31
 * @since 1.0
 */
@Slf4j
public final class EqualUtil {

    private EqualUtil() {

    }

    /**
     * check equal.
     *
     * @param value1 Integer
     * @param value2 Integer
     * @return boolean
     */
    public static boolean isEq(Integer value1, Integer value2) {
        if (Objects.nonNull(value1) && Objects.nonNull(value2)) {
            return value1.intValue() == value2.intValue();
        }
        return false;
    }

    /**
     * check equal.
     *
     * @param value1 Long
     * @param value2 Long
     * @return boolean
     */
    public static boolean isEq(Long value1, Long value2) {
        if (Objects.nonNull(value1) && Objects.nonNull(value2)) {
            return value1.longValue() == value2.longValue();
        }
        return false;
    }

    /**
     * check equal.
     *
     * @param str1 String
     * @param str2 String
     * @return boolean
     */
    public static boolean isEq(String str1, String str2) {
        return isEq(str1, str2, true);
    }

    /**
     * check equal
     *
     * @param str1          String
     * @param str2          String
     * @param caseSensitive boolean
     * @return boolean
     */
    public static boolean isEq(String str1, String str2, boolean caseSensitive) {
        if (Objects.nonNull(str1) && Objects.nonNull(str2)) {
            return caseSensitive ? str1.equals(str2) : str1.equalsIgnoreCase(str2);
        }
        return false;
    }

    /**
     * 判断两个集合是否相等
     *
     * @param set1 Collection
     * @param set2 Collection
     * @return boolean
     */
    public static boolean isEq(final Collection<?> set1, final Collection<?> set2) {
        return SetUtils.isEqualSet(set1, set2);
    }

    public enum DateMode {
        YEAR,
        MONTH,
        DAY,
        HOUR,
        MINUTE,
        SECOND,
        MIllI_SECOND
    }

    /**
     * 日期比较
     *
     * @param date1 datetime
     * @param date2 datetime
     * @return
     */
    public static boolean isEq(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            log.warn("date1 or date2 is null, plz check.");
            return false;
        }

        return date1.getTime() == date2.getTime();
    }

    /**
     * 日期比较
     *
     * @param date1    datetime
     * @param date2    datetime
     * @param dateMode date mode
     * @return
     */
    public static boolean isEq(final Date date1, final Date date2, DateMode dateMode) {
        if (date1 == null || date2 == null) {
            log.warn("date1 or date2 is null, plz check.");
            return false;
        }
        Preconditions.checkNotNull(dateMode, "日期比较模式不能为空");

        String format = "";
        switch (dateMode) {
            case YEAR:
                format = "yyyy";
                break;
            case MONTH:
                format = "yyyyMM";
                break;
            case DAY:
                format = "yyyyMMdd";
                break;
            case HOUR:
                format = "yyyyMMddHH";
                break;
            case MINUTE:
                format = "yyyyMMddHHmm";
                break;
            case SECOND:
                format = "yyyyMMddHHmmss";
                break;
            case MIllI_SECOND:
                format = "yyyyMMddHHmmssSSS";
                break;
            default:

                break;
        }
        if (format.isEmpty()) {
            log.warn("format is empty.");
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String left = sdf.format(date1);
        String right = sdf.format(date2);

        return left.equalsIgnoreCase(right);
    }


    //---不等操作

    /**
     * is not equal check.
     *
     * @param value1 Integer
     * @param value2 Integer
     * @return boolean
     */
    public static boolean isNotEq(Integer value1, Integer value2) {
        return !isEq(value1, value2);
    }

    /**
     * is not equal check.
     *
     * @param baseVar
     * @param value1
     * @param value2
     * @return
     */
    public static boolean isNotEq(Integer baseVar, Integer value1, Integer value2) {
        return (!isEq(baseVar, value1)) && (!isEq(baseVar, value2));
    }

    /**
     * is not equal check.
     *
     * @param baseVar
     * @param value1
     * @param value2
     * @param value3
     * @return
     */
    public static boolean isNotEq(Integer baseVar, Integer value1, Integer value2, Integer value3) {
        return (!isEq(baseVar, value1)) && (!isEq(baseVar, value2)) && (!isEq(baseVar, value3));
    }

    /**
     * is not equal check.
     *
     * @param value1 Long
     * @param value2 Long
     * @return boolean
     */
    public static boolean isNotEq(Long value1, Long value2) {
        return !isEq(value1, value2);
    }

    /**
     * all not equal
     *
     * @param baseVar base variable
     * @param value1  value1
     * @param value2  value2
     * @return
     */
    public static boolean isNotEq(Long baseVar, Long value1, Long value2) {
        return (!isEq(baseVar, value1)) && (!isEq(baseVar, value2));
    }

    /**
     * all not equal
     *
     * @param baseVar base variable
     * @param value1  v1
     * @param value2  v2
     * @param value3  v3
     * @return
     */
    public static boolean isNotEq(Long baseVar, Long value1, Long value2, Long value3) {
        return (!isEq(baseVar, value1)) && (!isEq(baseVar, value2)) && (!isEq(baseVar, value3));
    }

    /**
     * all not equal
     *
     * @param str1 string
     * @param str2 string
     * @return boolean
     */
    public static boolean isNotEq(String str1, String str2) {
        return !isEq(str1, str2);
    }


    /**
     * all not equal
     *
     * @param baseVar base var
     * @param str1    string
     * @param str2    string
     * @return boolean
     */
    public static boolean isNotEq(String baseVar, String str1, String str2) {
        return (!isEq(baseVar, str1)) && (!isEq(baseVar, str2));
    }

    /**
     * all not equal
     *
     * @param baseVar base var
     * @param str1    string
     * @param str2    string
     * @param str3    string
     * @return boolean
     */
    public static boolean isNotEq(String baseVar, String str1, String str2, String str3) {
        return (!isEq(baseVar, str1)) && (!isEq(baseVar, str2)) && (!isEq(baseVar, str3));
    }

    /**
     * is not equal check
     *
     * @param str1          string
     * @param str2          string
     * @param caseSensitive boolean
     * @return boolean
     */
    public static boolean isNotEq(String str1, String str2, boolean caseSensitive) {
        return !isEq(str1, str2, caseSensitive);
    }

    /**
     * 判断当前字符是否在数组中
     *
     * @param str1
     * @param strArray
     * @return
     */
    public static boolean isIn(String str1, String... strArray) {
        Preconditions.checkNotNull(str1);
        Preconditions.checkNotNull(strArray);

        return Arrays.stream(strArray).anyMatch(item -> isEq(str1, item));
    }

    public static boolean isNotIn(String str, String... strArray) {
        return !isIn(str, strArray);
    }

    /**
     * 判断当前value是否在其中
     *
     * @param value
     * @param values
     * @return
     */
    public static boolean isIn(Integer value, Integer... values) {
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(values);

        return Arrays.stream(values).anyMatch(item -> item.intValue() == value.intValue());
    }

    public static boolean isNotIn(Integer value, Integer... values) {
        return !isIn(value, values);
    }

    /**
     * 判断是否在其中
     *
     * @param value
     * @param values
     * @return
     */
    public static boolean isIn(Long value, Long... values) {
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(values);

        return Arrays.stream(values).anyMatch(item -> item.longValue() == value.longValue());
    }

    public static boolean isNotIn(Long value, Long... values) {
        return !isIn(value, values);
    }

    /**
     * 判断value是否在values中
     *
     * @param value  待检测变量
     * @param values 集合
     * @return
     */
    public static boolean isIn(String value, List<String> values) {
        if (value == null || values == null) {
            log.warn("value or values is null");
            return false;
        }
        return values.stream().anyMatch(item -> isEq(item, value));
    }

    public static boolean isNotIn(String value, List<String> values) {
        return !isIn(value, values);
    }

    /**
     * 判断value是否在values中
     *
     * @param value  待检测变量
     * @param values 集合
     * @return
     */
    public static boolean isIn(Integer value, List<Integer> values) {
        if (value == null || values == null) {
            log.warn("value or values is null");
            return false;
        }

        return values.stream().anyMatch(item -> item.intValue() == value.intValue());
    }

    public static boolean isNotIn(Integer value, List<Integer> values) {
        return !isIn(value, values);
    }

    /**
     * 判断value是否在values中
     *
     * @param value  待检测变量
     * @param values 集合
     * @return
     */
    public static boolean isIn(Long value, List<Long> values) {
        if (value == null || values == null) {
            log.warn("value or values is null");
            return false;
        }

        return values.stream().anyMatch(item -> item.longValue() == value.longValue());
    }

    public static boolean isNotIn(Long value, List<Long> values) {
        return !isIn(value, values);
    }

    /**
     * check all element in left side must be in right side
     *
     * @param left  small one
     * @param right big one
     * @return
     */
    public static <T> boolean isIn(Collection<T> left, Collection<T> right) {
        Preconditions.checkNotNull(left, "left cannot be null");
        Preconditions.checkNotNull(right, "right cannot be null");
        return right.containsAll(left);
    }

    /**
     * check all element in left side must be in right side.
     *
     * @param left
     * @param right
     * @param <T>
     * @return
     */
    public static <T> boolean isAllNotIn(Collection<T> left, Collection<T> right) {
        Preconditions.checkNotNull(left, "");
        Preconditions.checkNotNull(right, "");
        if (left.isEmpty()) {
            return true;
        }
        boolean allNotIn = true;
        Iterator<T> it = left.iterator();
        while (it.hasNext()) {
            T obj = it.next();
            allNotIn = allNotIn && (!right.contains(obj));
            if (!allNotIn) {
                return false;
            }
        }

        return true;
    }

}
