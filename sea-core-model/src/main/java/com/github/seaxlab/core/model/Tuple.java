package com.github.seaxlab.core.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 值元组，一般用于返回多个对象
 *
 * @author spy
 * @version 1.0 11/19/20
 * @since 1.0
 */
public class Tuple implements Iterable<Object>, Serializable {

  /**
   * field
   */
  private Object[] members;

  /**
   * 构造
   *
   * @param members 成员数组
   */
  public Tuple(Object... members) {
    this.members = members;
  }


  /**
   * 静态组装方法
   *
   * @param members
   * @return
   */
  public static Tuple of(Object... members) {
    return new Tuple(members);
  }

  /**
   * 获取指定位置元素
   *
   * @param <T>   返回对象类型
   * @param index 位置
   * @return 元素
   */
  @SuppressWarnings("unchecked")
  public <T> T get(int index) {
    return (T) members[index];
  }

  /**
   * 获得所有元素
   *
   * @return 获得所有元素
   */
  public Object[] getMembers() {
    return this.members;
  }

  /**
   * 对象长度
   *
   * @return
   */
  public int length() {
    return members == null ? 0 : members.length;
  }

  @Override
  public String toString() {
    return Arrays.toString(members);
  }

  @Override
  public Iterator<Object> iterator() {
    return Arrays.asList(members).iterator();
  }

}
