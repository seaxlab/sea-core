package com.github.spy.sea.core.component.sort.impl;

import com.github.spy.sea.core.component.sort.Sort;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单选择排序
 * <p>
 * 在要排序的一组数中，选出最小的一个数与第一个位置的数交换；然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止
 * </p>
 *
 * @author spy
 * @version 1.0 2021/4/25
 * @since 1.0
 */
@Slf4j
public class SimpleSelectSort implements Sort {

    @Override
    public void sort(int[] array) {
        int position = 0;
        for (int i = 0; i < array.length; i++) {
            int j = i + 1;
            position = i;
            int temp = array[i];
            for (; j < array.length; j++) {
                if (array[j] < temp) {
                    temp = array[j];
                    position = j;
                }
            }
            array[position] = array[i];
            array[i] = temp;
        }
    }
}
