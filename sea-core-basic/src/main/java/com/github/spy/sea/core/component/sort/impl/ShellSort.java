package com.github.spy.sea.core.component.sort.impl;

import com.github.spy.sea.core.component.sort.Sort;
import lombok.extern.slf4j.Slf4j;

/**
 * 希尔排序
 * <p>
 * 算法先将要排序的一组数按某个增量d（n/2,n为要排序数的个数）分成若干组，每组中记录的下标相差d.对每组中全部元素进行直接插入排序，然后再用一个较小的增量（d/2）对它进行分组，在每组中再进行直接插入排序。当增量减到1时，进行直接插入排序后，排序完成。
 * </p>
 *
 * @author spy
 * @version 1.0 2021/4/25
 * @since 1.0
 */
@Slf4j
public class ShellSort implements Sort {

    @Override
    public void sort(int[] array) {
        double d1 = array.length;
        int temp = 0;

        while (true) {
            d1 = Math.ceil(d1 / 2);
            int d = (int) d1;
            for (int x = 0; x < d; x++) {

                for (int i = x + d; i < array.length; i += d) {
                    int j = i - d;
                    temp = array[i];
                    for (; j >= 0 && temp < array[j]; j -= d) {
                        array[j + d] = array[j];
                    }
                    array[j + d] = temp;
                }
            }

            if (d == 1) {
                break;
            }
        } // end while
    }
}
