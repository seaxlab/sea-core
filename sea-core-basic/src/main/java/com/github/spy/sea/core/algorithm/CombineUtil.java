package com.github.spy.sea.core.algorithm;

import com.github.spy.sea.core.annotation.Beta;
import com.github.spy.sea.core.exception.ExceptionHandler;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/10/30
 * @since 1.0
 */
@Slf4j
@Beta
public final class CombineUtil {

    /**
     * 计算n的阶乘：n! = n * (n-1) * (n-2) * ... *2 * 1
     */
    public static long factorial(int n) {
        return (n > 1) ? n * factorial(n - 1) : 1;
    }

    /**
     * 计算排列数：A(n, m) = n!/(n-m)!  -- 从n个数中取出m个数进行排列 ,需要考虑数的顺序 (如果n个数进行排列，有n!种情况)
     */
    public static long arrangement(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) : 0;
    }

    /**
     * 计算组合数：C(n, m) = n!/((n-m)! * m!)  --  从n个数中取出m个数进行排列 ,不考虑数的顺序 （如 1234 和 4321 属于一种组合，都包含1，2，3，4这四个数）
     */
    public static long combination(int m, int n) {
        return (n >= m) ? factorial(n) / (factorial(n - m) * factorial(m)) : 0;
    }

    /**
     * 排列：从数组a中选择n个数进行排列
     */
    public static void arrangementSelect(int[] a, int n) {
        System.out.println(String.format("A(%d, %d) = %d", a.length, n, arrangement(a.length, n)));
        arrangementSort(a, new int[n], 0);
    }

    /**
     * 通过递归的方式罗列出所有的排列结果
     *
     * @param a：初始数组
     * @param result：排列数组初始状态
     * @param resultIndex：比较的起始索引
     */
    public static void arrangementSort(int[] a, int[] result, int resultIndex) {
        int result_length = result.length;
        if (resultIndex >= result_length) {
            System.out.println(Arrays.toString(result));  // 输出排列结果
            return;
        }
        //
        for (int i = 0; i < a.length; i++) {
            // 判断待选的数是否存在于排列的结果中
            boolean exist = false;
            for (int j = 0; j < resultIndex; j++) {
                if (a[i] == result[j]) {  // 若已存在，则不能重复选
                    exist = true;
                    break;
                }
            }
            if (!exist) {  // 若不存在，则可以选择
                result[resultIndex] = a[i];
                arrangementSort(a, result, resultIndex + 1);
            }
        }
    }

    /**
     * 组合：从数组a中选择n个数进行组合
     */
    public static void combinationSelect(int a[], int n) {
        System.out.println(String.format("C(%d, %d)= %d", a.length, n, combination(a.length, n)));
        combinationSort(a, 0, new int[a.length], 0);

    }

    /**
     * 通过递归的方式罗列出所有的组合结果
     *
     * @param a：初始数组
     * @param a_index：初始数组起始下标
     * @param result：初始组合数组
     * @param r_index：初始组合数组的起始下标
     */
    public static void combinationSort(int[] a, int a_index, int[] result, int r_index) {
        int r_len = result.length;
        int r_count = r_index + 1;
        if (r_count > r_len) {
            System.out.println(Arrays.toString(result));  // 输出组合结果
            return;
        }
        for (int i = a_index; i < a.length + r_count - r_len; i++) {
            result[r_index] = a[i];
            combinationSort(a, i + 1, result, r_index + 1);
        }
    }

    /**
     * 分组
     *
     * @param set
     * @param splitSize
     */
    public static Set<Set<String>> split(Set<String> set, int splitSize) {
        Preconditions.checkState(splitSize > 0, "分组模式数必须大于1");
        // 只有一组
        if (splitSize == 1) {
            Set<Set<String>> all = new HashSet<>(1);
            all.add(set);
            return all;
        }

        // 全分组
        if (splitSize == set.size()) {
            return set.stream()
                      .map(item -> {
                          Set<String> container = new HashSet<>(1);
                          container.add(item);
                          return container;
                      }).collect(Collectors.toSet());
        }

        // 中间分组
        //TODO
        switch (splitSize) {
            case 2:
                break;
            case 3:
                break;
            default:
                ExceptionHandler.publishMsg("不支持的分组计算");
                break;
        }


        return Collections.emptySet();
    }

    private static Set<String> split2Part(Set<String> set) {
        return null;
    }


}
