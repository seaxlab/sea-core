package com.github.seaxlab.core.component.sort;

/**
 * 分类：
 * <p>
 * 1）插入排序（直接插入排序、希尔排序）<br>
 * 2）交换排序（冒泡排序、快速排序）<br>
 * 3）选择排序（直接选择排序、堆排序）<br>
 * 4）归并排序<br>
 * 5）分配排序（基数排序）<br>
 * 所需辅助空间最多：归并排序<br>
 * 所需辅助空间最少：堆排序<br>
 * 平均速度最快：快速排序o(nlogn)<br>
 * </p>
 * <p>
 * 不稳定的排序算法：堆排序、快速排序、希尔排序、直接选择排序 <br>
 * 稳定的排序算法：基数排序、冒泡排序、直接插入排序、折半插入排序、归并排序
 * </p>
 *
 * @author spy
 * @version 1.0 2021/4/25
 * @since 1.0
 */
public interface Sort {

    /**
     * 排序
     *
     * @param array
     */
    void sort(int[] array);
}
