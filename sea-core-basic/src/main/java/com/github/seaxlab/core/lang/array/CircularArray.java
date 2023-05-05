package com.github.seaxlab.core.lang.array;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 环形数组实现，非线程安全
 * <p>
 * 若需保证线程安全只需要在使用时对addLast和clear方法加锁即可
 * </p>
 *
 * @author spy
 * @version 1.0 2023/5/5
 * @since 1.0
 */
@Slf4j
public class CircularArray<E> implements Iterable<E> {
  /**
   * 环形数组
   */
  private final AtomicReferenceArray<E> circularArray;
  /**
   * 头指针
   */
  private int head;
  /**
   * 尾指针
   */
  private int tail;
  /**
   * 当前数组内元素的个数
   */
  private int size;
  /**
   * 环形数组容量
   */
  private final int capacity;

  public CircularArray(int capacity) {
    circularArray = new AtomicReferenceArray<>(capacity);
    head = 0;
    tail = 0;
    this.capacity = capacity;
  }

  public CircularArray(AtomicReferenceArray<E> circularArray) {
    this.circularArray = circularArray;
    head = 0;
    tail = 0;
    capacity = circularArray.length();
  }

  public void addLast(E bucket) {
    // 已经到达最后一个
    if (size == capacity) {
      if (head == capacity - 1) {
        head = 0;
      } else {
        head = head + 1;
      }
      if (tail == capacity) {
        circularArray.set(0, bucket);
        tail = 1;
      } else {
        circularArray.set(tail, bucket);
        tail = tail + 1;
      }
    } else {
      // 环形数组中元素个数还未达到capacity，则只移动tail
      circularArray.set(tail, bucket);
      tail = tail + 1;
      size++;
    }
  }

  /**
   * 清除环形数组
   */
  public void clear() {
    size = 0;
    head = 0;
    tail = 0;
  }

  /**
   * 在内部数组的副本上返回一个迭代器，以便迭代器不会因同时添加删除的存储桶而失败。
   */
  @Override
  public Iterator<E> iterator() {
    // 获取环形数组里的所有元素，这里获取到的是环形数组里的元素的副本
    return Collections.unmodifiableList(Arrays.asList(getArray())).iterator();
  }

  public List<E> getList() {
    List<E> data = new ArrayList<>();
    // 依次获取环形数组内部所有元素并加入到新的list
    for (int i = 0; i < size; i++) {
      data.add(circularArray.get(convert(i)));
    }
    return data;
  }

  /**
   * 获取环形数组中所有元素
   */
  public E[] getArray() {
    List<E> data = getList();
    return (E[]) data.toArray();
  }

  /**
   * convert() 方法采用逻辑索引（好像 head 始终为 0）并计算 elementData 内的索引
   */
  private int convert(int index) {
    return (index + head) % (capacity);
  }
}
