package com.github.seaxlab.core.util.model;

import java.util.List;

/**
 * tree model
 *
 * @author spy
 * @version 1.0 2025/6/3
 * @since 1.0
 */
public interface TreeService<T extends TreeService<?>> {

  /**
   * 当前id
   * @return
   */
  String getId();

  /**
   * 父id
   * @return
   */
  String getParentId();

  /**
   * 排序
   * @return
   */
  Long getSort();

  /**
   * 子列表
   * @return
   */
  List<T> getChildren();

  /**
   * 设置子列表
   * @param children
   */
  void setChildren(List<T> children);
}
