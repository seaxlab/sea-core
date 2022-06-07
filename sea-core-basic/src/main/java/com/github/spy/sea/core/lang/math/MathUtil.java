package com.github.spy.sea.core.lang.math;

import cn.hutool.core.math.Combination;
import com.github.spy.sea.core.model.Result;
import com.github.spy.sea.core.util.ArrayUtil;
import com.github.spy.sea.core.util.SetUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * complex operation of math
 * <p>
 * Arrangement and Combination
 *
 * @author spy
 * @version 1.0 11/19/20
 * @since 1.0
 */
@Slf4j
public final class MathUtil {

    /**
     * get min value
     *
     * @param numbers
     * @return
     */
    public static BigDecimal min(Number... numbers) {
        if (numbers == null || numbers.length == 0) {
            return null;
        }

        List<BigDecimal> list = new ArrayList<>(numbers.length);
        for (Number item : numbers) {
            if (item == null) {
                continue;
            }
            list.add(new BigDecimal(item.toString()));
        }
        list.sort(Comparator.comparing(BigDecimal::doubleValue));
        return list.get(0);
    }

    /**
     * get max value.
     *
     * @param numbers
     * @return
     */
    public static BigDecimal max(Number... numbers) {
        if (numbers == null || numbers.length == 0) {
            return null;
        }

        List<BigDecimal> list = new ArrayList<>(numbers.length);
        for (Number item : numbers) {
            if (item == null) {
                continue;
            }
            list.add(new BigDecimal(item.toString()));
        }
        list.sort(Comparator.comparing(BigDecimal::doubleValue).reversed());
        return list.get(0);
    }

    /**
     * check is power of 2.
     *
     * @param val
     * @return
     */
    public static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }

    /**
     * Fast method of finding the next power of 2 greater than or equal to the supplied value.
     *
     * <p>If the value is {@code <= 0} then 1 will be returned.
     * This method is not suitable for {@link Integer#MIN_VALUE} or numbers greater than 2^30.
     *
     * @param value from which to search for next power of 2
     * @return The next power of 2 or the value itself if it is a power of 2
     */
    public static int findNextPositivePowerOfTwo(final int value) {
        assert value > Integer.MIN_VALUE && value < 0x40000000;
        return 1 << (32 - Integer.numberOfLeadingZeros(value - 1));
    }

    /**
     * Fast method of finding the next power of 2 greater than or equal to the supplied value.
     * <p>This method will do runtime bounds checking and call {@link #findNextPositivePowerOfTwo(int)} if within a
     * valid range.
     *
     * @param value from which to search for next power of 2
     * @return The next power of 2 or the value itself if it is a power of 2.
     * <p>Special cases for return values are as follows:
     * <ul>
     *     <li>{@code <= 0} -> 1</li>
     *     <li>{@code >= 2^30} -> 2^30</li>
     * </ul>
     */
    public static int safeFindNextPositivePowerOfTwo(final int value) {
        return value <= 0 ? 1 : value >= 0x40000000 ? 0x40000000 : findNextPositivePowerOfTwo(value);
    }


    //--------------------------------------------------------------------------------------------- Arrangement

    /**
     * 计算排列数，即A(n, m) = n!/(n-m)!
     *
     * @param n 总数
     * @param m 选择的个数
     * @return 排列数
     */
    public static long arrangementCount(int n, int m) {
        return cn.hutool.core.math.MathUtil.arrangementCount(n, m);
    }

    /**
     * 计算排列数，即A(n, n) = n!
     *
     * @param n 总数
     * @return 排列数
     */
    public static long arrangementCount(int n) {
        return cn.hutool.core.math.MathUtil.arrangementCount(n);
    }

    /**
     * 排列选择（从列表中选择n个排列）
     *
     * @param datas 待选列表
     * @param m     选择个数
     * @return 所有排列列表
     */
    public static List<String[]> arrangementSelect(String[] datas, int m) {
        return cn.hutool.core.math.MathUtil.arrangementSelect(datas, m);
    }

    /**
     * 全排列选择（列表全部参与排列）
     *
     * @param datas 待选列表
     * @return 所有排列列表
     */
    public static List<String[]> arrangementSelect(String[] datas) {
        return cn.hutool.core.math.MathUtil.arrangementSelect(datas);
    }

    //--------------------------------------------------------------------------------------------- Combination

    /**
     * 计算组合数，即C(n, m) = n!/((n-m)! * m!)
     *
     * @param n 总数
     * @param m 选择的个数
     * @return 组合数
     */
    public static long combinationCount(int n, int m) {
        return cn.hutool.core.math.MathUtil.combinationCount(n, m);
    }

    /**
     * 组合选择（从列表中选择n个组合）
     *
     * @param datas 待选列表
     * @param m     选择个数
     * @return 所有组合列表
     */
    public static List<String[]> combinationSelect(String[] datas, int m) {
        return cn.hutool.core.math.MathUtil.combinationSelect(datas, m);
    }

    /**
     * 组合选择- 全组合
     *
     * @param datas 待选列表
     * @return
     */
    public static List<String[]> combinationSelectAll(String[] datas) {
        return new Combination(datas).selectAll();
    }

    /**
     * 全组合后，无序
     * <p>
     * 待组合列表：1,2,3
     * (1),(2,3)
     * (2),(1,3)
     * (3),(1,2)
     * (1,2),(3) // 重复
     * (1,3),(2) // 重复
     * (2,3),(1)
     * (1,2,3)
     * </p>
     *
     * @param data
     * @return
     */
    public static List<String[]> combinationSelectAllNoOrder(String[] data) {
        List<String[]> list = combinationSelectAll(data);

        Set<String> allSet = SetUtil.toSet(data);
        int loop = list.size() % 2 == 0 ? list.size() / 2 : (list.size() / 2 + 1);
        for (int i = 0; i < loop; i++) {
            String[] item = list.get(i);
            if (item == null) {
                continue;
            }
            Set<String> first = SetUtil.toSet(item);

            for (int j = i; j < list.size(); j++) {
                String[] item2 = list.get(j);
                if (item2 == null) {
                    continue;
                }
                Set<String> second = SetUtil.toSet(item2);
                if ((first.size() + second.size() == allSet.size())
                        && SetUtil.union(first, second).size() == allSet.size()) {
                    list.set(j, null);
                }
            }
        }

        return list.stream().filter(item -> item != null).collect(Collectors.toList());
    }

    /**
     * 找出第一个满足count个连续的数组的下标
     *
     * @param array
     * @param count
     * @return
     */
    public static Result<Integer> findContinuousIndex(int[] array, int count) {
        Result<Integer> result = Result.fail();
        if (array.length < count) {
            result.setMsg("数据太少");
            return result;
        }

        boolean flag;
        for (int i = count; i < array.length; i++) {
            int[] subArray = ArrayUtil.sub(array, i - count, i);
            flag = isContinuous(subArray);
            if (flag) {
                result.value(i - count);
                break;
            }
        }

        return result;
    }

    /**
     * 判断整个数组是否连续
     *
     * @param array 升序数据
     * @return
     */
    public static boolean isContinuous(int[] array) {
        boolean flag = true;
        for (int i = 0; i < array.length - 1; i++) {
            int start = array[i];
            int next = array[i + 1];
            if (start + 1 != next) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    /**
     * 判断是否连续，
     *
     * @param array 倒序数组
     * @return
     */
    public static boolean isContinuousDesc(int[] array) {
        boolean flag = true;
        for (int i = 0; i < array.length - 1; i++) {
            int start = array[i];
            int next = array[i + 1];
            if (start - 1 != next) {
                flag = false;
                break;
            }
        }

        return flag;
    }

}
