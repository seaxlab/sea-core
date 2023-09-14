package com.github.seaxlab.core.component.template.cache;

import java.util.List;

/**
 * i list query cache
 *
 * @author spy
 * @version 1.0 2023/09/14
 * @since 1.0
 */
public interface IListQueryCache<V> {

  /**
   * 获取多个列表
   *
   * @return list
   */
  List<V> getList();

}
