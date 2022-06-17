package com.github.seaxlab.core.component.sort.impl;

import com.github.seaxlab.core.component.sort.Sort;
import lombok.extern.slf4j.Slf4j;

/**
 * 插入排序
 * <p>
 * 在要排序的一组数中，假设前面(n-1)[n>=2] 个数已经是排好顺序的，现在要把第n个数插到前面的有序数中，使得这n个数
 * 也是排好顺序的。如此反复循环，直到全部排好顺序
 * </p>
 *
 * @author spy
 * @version 1.0 2021/4/25
 * @since 1.0
 */
@Slf4j
public class InsertSort implements Sort {

    @Override
    public void sort(int[] array) {
        int temp = 0;
        for (int i = 1; i < array.length; i++) {
            int j = i - 1;
            temp = array[i];
            for (; j >= 0 && temp < array[j]; j--) {
                array[j + 1] = array[j];  //将大于temp的值整体后移一个单位
            }
            array[j + 1] = temp;
        }
    }
}
