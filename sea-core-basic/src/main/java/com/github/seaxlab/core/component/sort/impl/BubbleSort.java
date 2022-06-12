package com.github.seaxlab.core.component.sort.impl;

import com.github.seaxlab.core.component.sort.Sort;
import lombok.extern.slf4j.Slf4j;

/**
 * 冒泡排序
 * <p>
 * 在要排序的一组数中，对当前还未排好序的范围内的全部数，自上而下对相邻的两个数依次进行比较和调整，让较大的数往下沉，较小的往上冒。
 * 即：每当两相邻的数比较后发现它们的排序与排序要求相反时，就将它们互换。
 * </p>
 *
 * @author spy
 * @version 1.0 2021/4/25
 * @since 1.0
 */
@Slf4j
public class BubbleSort implements Sort {
    @Override
    public void sort(int[] array) {
        int temp = 0;

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}


