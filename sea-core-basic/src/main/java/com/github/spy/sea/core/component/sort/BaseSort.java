package com.github.spy.sea.core.component.sort;

import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/3
 * @since 1.0
 */
@Slf4j
public class BaseSort {
    public static void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
